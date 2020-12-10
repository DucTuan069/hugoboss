package com.Boss.payment;

import com.Boss.ResponseAPI;
import com.Boss.donHang.DonHang;
import com.Boss.donHang.DonHangRepository;
import com.Boss.donHang.TrangThaiDonHang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/payment/vnp")
public class PaymentController {

    @Autowired
    private DonHangRepository donHangRepository;

    @GetMapping("get-payment-link")
    public ResponseEntity getPaymentLink(HttpServletRequest req){
        String vnp_Version = "2.0.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = req.getParameter("orderInfo");
        String order = req.getParameter("orders");
        String vnp_TxnRef = order;
        String vnp_IpAddr = Config.getIpAddress(req);

        String[] orders = order.split(",");
        int totalMoney = 0;
        for (int i=0;i<orders.length;i++){
            DonHang dh = this.donHangRepository.findById(Integer.parseInt(orders[i].trim())).orElse(null);
            if(dh == null) continue;
            totalMoney += dh.getTongTien();
        }

        String vnp_TmnCode = Config.vnp_TmnCode;

        String vnp_TransactionNo = vnp_TxnRef;
        String vnp_hashSecret = Config.vnp_HashSecret;

        int amount = totalMoney * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = req.getParameter("bankcode");
        if (bank_code != null && bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
//        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Merchant", "DEMO");

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(dt);
        String vnp_CreateDate = dateString;
        String vnp_TransDate = vnp_CreateDate;
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
                //Build query
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.Sha256(Config.vnp_HashSecret + hashData.toString());
        //System.out.println("HashData=" + hashData.toString());
        queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        ResponseAPI response = new ResponseAPI();
        response.Data = paymentUrl;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("ipn-return")
    public ResponseEntity ipnPayment(HttpServletRequest request){
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = null;
        try {
            signValue = Config.hashAllFields(fields);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (signValue.equals(vnp_SecureHash)) {
            //Kiem tra chu ky OK
            /* Kiem tra trang thai don hang trong DB: checkOrderStatus,
            - Neu trang thai don hang OK, tien hanh cap nhat vao DB, tra lai cho VNPAY RspCode=00
            - Neu trang thai don hang (da cap nhat roi) => khong cap nhat vao DB, tra lai cho VNPAY RspCode=02
            */
            boolean checkOrderStatus = true;
            String order = request.getParameter("vnp_TxnRef");
            String[] orders = order.split(",");

            for (int i=0;i<orders.length;i++){
                DonHang dh = this.donHangRepository.findById(Integer.parseInt(orders[i].trim())).orElse(null);
                if(dh == null) checkOrderStatus = false;
                checkOrderStatus = dh.getTrangThaiDonHang() != TrangThaiDonHang.DaThanhToan;
                if(!checkOrderStatus) break;
            }

            if (checkOrderStatus) {
                if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                    for (int i=0;i<orders.length;i++){
                        DonHang dh = this.donHangRepository.findById(Integer.parseInt(orders[i].trim())).orElse(null);
                        dh.setTrangThaiDonHang(TrangThaiDonHang.DaThanhToan);
                        this.donHangRepository.save(dh);
                    }
                    return new ResponseEntity("{\"RspCode\":\"00\",\"Message\":\"Thanh toán thành công\"}", HttpStatus.OK);
                } else {
                    for (int i=0;i<orders.length;i++){
                        DonHang dh = this.donHangRepository.findById(Integer.parseInt(orders[i].trim())).orElse(null);
                        dh.setTrangThaiDonHang(TrangThaiDonHang.ThanhToanThatBai);
                        this.donHangRepository.save(dh);
                    }
                    return new ResponseEntity("{\"RspCode\":\"01\",\"Message\":\"Thanh toán thất bại, vui lòng kiểm tra lại tài khoản\"}", HttpStatus.OK);
                }
            } else {
                //Don hang nay da duoc cap nhat roi, Merchant khong cap nhat nua (Duplicate callback)
                return new ResponseEntity("{\"RspCode\":\"02\",\"Message\":\"Đơn hàng đã được xác nhận thanh toán\"}", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity("{\"RspCode\":\"97\",\"Message\":\"Không hợp lệ\"}", HttpStatus.OK);
        }
    }

    @PutMapping("cod/{donhangId}")
    public ResponseEntity setCod(@PathVariable int donhangId, HttpServletRequest request){
        ResponseAPI res = new ResponseAPI();

        DonHang dh = this.donHangRepository.findById(donhangId).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có id = " + donhangId));

        if(!dh.getMaVanDon().isEmpty()){
            res.Code = -1;
            res.Message = "Không thể thực hiện hành động này";
            return new ResponseEntity(res, HttpStatus.OK);
        }

        switch (dh.getTrangThaiDonHang()){
            case DaThanhToan:
            case ChoThanhToan:
            case HoanTra:
                res.Code = -1;
                res.Message = "Không thể thực hiện hành động này";
                return new ResponseEntity(res, HttpStatus.OK);
        }

        dh.setTrangThaiDonHang(TrangThaiDonHang.ChoThanhToan);
        this.donHangRepository.save(dh);

        return new ResponseEntity(res, HttpStatus.OK);
    }
}
