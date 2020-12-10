package com.Boss.donHang;

import com.Boss.ResponseAPI;
import com.Boss.cuaHangSanPham.CuaHangSanPham;
import com.Boss.cuaHangSanPham.CuaHangSanPhamRepository;
import com.Boss.cuaHangSanPham.SoLuongSanPhamRequestData;
import com.Boss.gioHang.GioHang;
import com.Boss.gioHang.GioHangRepository;
import com.Boss.gioHang.GioHangSanPhamRepository;
import com.Boss.product.SanPham;
import com.Boss.product.SanPhamRepository;
import com.Boss.shipping.ShippingFee;
import com.Boss.shipping.ShippingService;
import com.Boss.shipping.GHN.GHNService;
import com.Boss.shipping.GHTK.GHTKService;
import com.Boss.shop.CuaHang;
import com.Boss.shop.CuaHangRepository;
import com.Boss.user.LoaiTaiKhoan;
import com.Boss.user.TaiKhoan;
import com.Boss.user.TaiKhoanDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.Boss.user.LoaiTaiKhoan.KhachHang;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/don-hang")
public class DonHangController {

    @Autowired
    private DonHangRepository donHangRepository;
    
    @Autowired
    private GioHangSanPhamRepository gioHangSanPhamRepository;
    
    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Autowired
    private GHNService ghnService;

    @Autowired
    private GHTKService ghtkService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private DonHangSanPhamRepository donHangSanPhamRepository;

    @Autowired
    private CuaHangSanPhamRepository cuaHangSanPhamRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @RequestMapping(value = "{donHangId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseAPI> chiTietDonHang(@PathVariable int donHangId){
        List<DonHangSanPham> dhsp = this.donHangSanPhamRepository.findByIdDonHang(donHangId).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có id = " + donHangId));

        ResponseAPI res = new ResponseAPI();
        res.Data = dhsp;
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResponseAPI> danhSachDonHang(@RequestParam(required = false) String from, @RequestParam(required = false) String to){
        from = from == null ? null : from + " 00:00:00";
        to = to == null ? null : to + " 23:59:59";

        ResponseAPI res = new ResponseAPI();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DonHang> dsDonHang = new ArrayList<>();

        if(principal instanceof TaiKhoanDetail){
            TaiKhoanDetail user = (TaiKhoanDetail) principal;
            TaiKhoan u = user.getUser();

            if(u.getLoaiTaiKhoan() == LoaiTaiKhoan.Admin){
                if(from == null || to == null){
                    dsDonHang = donHangRepository.findAll();
                }else{
                    dsDonHang = donHangRepository.findAllByDateRange(from, to);
                }
            }else if(u.getLoaiTaiKhoan() == LoaiTaiKhoan.KhachHang){
                if(from == null || to == null){
                    dsDonHang = u.getDanhSachDonHang();
                }else{
                    dsDonHang = donHangRepository.findAllByUserAndDateRange(u.getId(), from, to);
                }
            }else{
                List<DonHangSanPham> dhsp = (from == null || to == null) ? donHangSanPhamRepository.findByIdCuaHang(u.getCuaHang().getId()) : donHangSanPhamRepository.findByIdCuaHangAndDateRange(u.getCuaHang().getId(), from, to);
                for(int i=0;i<dhsp.size();i++){
                    dsDonHang.add( dhsp.get(i).getDonHang() );
                }
            }
        }

        for(int i=0;i<dsDonHang.size();i++){
            List<DonHangSanPham> dhsp = dsDonHang.get(i).getDsDonHangSanPham();
            if(dhsp.size() == 0) continue;
            dsDonHang.get(i).setTenCuaHang(dhsp.get(0).getCuaHang().getTenCuaHang());
        }

        Collections.reverse(dsDonHang);
        res.Data = dsDonHang;

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseAPI> taoDonHang(@RequestBody DonHangRequestData data){
        ResponseAPI res = new ResponseAPI();
        res.Data = new ArrayList<>();

        TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaiKhoan khachHang = user.getUser();
        List<SoLuongSanPhamRequestData> dsSanPham = data.getSanPham();

        Map<Integer, List<SoLuongSanPhamRequestData>> map = new HashMap<>();
        for(int i=0;i<dsSanPham.size();i++){
            SoLuongSanPhamRequestData sp = dsSanPham.get(i);
            if(!map.containsKey(sp.getIdCuaHang())){
                map.put(sp.getIdCuaHang(), new ArrayList<SoLuongSanPhamRequestData>());
            }
            map.get(sp.getIdCuaHang()).add(sp);
        }

        for (Map.Entry<Integer, List<SoLuongSanPhamRequestData>> entry : map.entrySet()) {
            List<SoLuongSanPhamRequestData> dsSoLuongSanPham = entry.getValue();

            CuaHang ch = this.cuaHangRepository.findById(entry.getKey()).orElseThrow(() -> new RuntimeException("Không tìm thấy cửa hàng có id = " + entry.getKey()));

            int tongTien = 0;
            List<SanPham> dsSp = new ArrayList<>();
            List<Integer> soLuong = new ArrayList<>();
            ShippingService shippingService = null;
            String dvvc = "";

            for(int i=0;i<dsSoLuongSanPham.size();i++){
                int spId = dsSoLuongSanPham.get(i).getIdSanPham();
                SanPham sp = this.sanPhamRepository.findById(spId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có id = " + spId));
                tongTien += sp.getGiaSale()*dsSoLuongSanPham.get(i).getSoLuong();
                dsSp.add(sp);
                soLuong.add(dsSoLuongSanPham.get(i).getSoLuong());
                dvvc = dsSoLuongSanPham.get(i).getShipping().toLowerCase();
                switch (dsSoLuongSanPham.get(i).getShipping().toLowerCase()){
                    case "ghtk": shippingService = this.ghtkService; break;
                    case "ghn": shippingService = this.ghnService; break;
                }
            }

            String[] from = ch.getDiaChi().split(",");
            String[] to = khachHang.getDiaChi().split(",");
            System.out.println( to[to.length-2]);
            ShippingFee fee = shippingService.getFee(ch.getDiaChi(), to[to.length-2]);
            DonHang donHang = new DonHang();
            donHang.setKhachHang(user.getUser());
            donHang.setShipping(dvvc);
            donHang.setShippingFee(fee.getFee());
            donHang.setTongTien(tongTien + fee.getFee());
            donHang.setMaVanDon("");

            if(data.getPayment().equals("cod")){
                donHang.setTrangThaiDonHang(TrangThaiDonHang.ChoThanhToan);
            }else{
                donHang.setTrangThaiDonHang(TrangThaiDonHang.TiepNhan);
            }

            donHang = this.donHangRepository.save(donHang);
            ((ArrayList)res.Data).add(donHang.getId());

            for(int i=0;i<dsSp.size();i++){
                DonHangSanPham dhsp = new DonHangSanPham();
                dhsp.setCuaHang(ch);
                dhsp.setDonHang(donHang);
                dhsp.setSanPham(dsSp.get(i));
                dhsp.setSoLuong(soLuong.get(i));
                dhsp = this.donHangSanPhamRepository.save(dhsp);

                List<CuaHangSanPham> chsp = this.cuaHangSanPhamRepository.findByCuaHangAndSanPham(ch.getId(), dsSanPham.get(i).getIdSanPham());
                if(chsp.size() > 0) {
                    chsp.get(0).setSoLuong( chsp.get(0).getSoLuong() - soLuong.get(i) );
                    this.cuaHangSanPhamRepository.save(chsp.get(0));
                }
            }
        }
        List<GioHang> idgiohang = this.gioHangRepository.findAllByUser(khachHang.getId());
        this.gioHangSanPhamRepository.deleteByGiohang(idgiohang.get(0).getId());
        this.gioHangRepository.deleteByTaiKhoan(khachHang.getId());

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequestMapping(value = "ma-van-don/{donHangId}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseAPI> updateMaVanDon(@PathVariable int donHangId, @RequestBody String maVanDon){
        ResponseAPI res = new ResponseAPI();

        DonHang dh = this.donHangRepository.findById(donHangId).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có id = " + donHangId));
        dh.setMaVanDon(maVanDon);
        if(dh.getTrangThaiDonHang() == TrangThaiDonHang.TiepNhan){
//            dh.setTrangThaiDonHang(TrangThaiDonHang.COD);
        }
        dh = this.donHangRepository.save(dh);

        res.Data = dh;

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequestMapping(value = "{donHangId}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseAPI> huyDon(@PathVariable int donHangId){
        ResponseAPI res = new ResponseAPI();

        DonHang dh = this.donHangRepository.findById(donHangId).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có id = " + donHangId));
        if(!dh.getMaVanDon().isEmpty()){
            res.Code = -1;
            res.Message = "Đơn hàng đã được vận chuyển, không thể hủy!";
            return new ResponseEntity(res, HttpStatus.OK);
        }

        dh.setTrangThaiDonHang(TrangThaiDonHang.HuyBo);
        dh = this.donHangRepository.save(dh);

        res.Data = dh;

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequestMapping(value = "{donHangId}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseAPI> updateTrangThai(@PathVariable int donHangId, @RequestBody int status){
        ResponseAPI res = new ResponseAPI();

        DonHang dh = this.donHangRepository.findById(donHangId).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có id = " + donHangId));

        try{
            TrangThaiDonHang trangThai = TrangThaiDonHang.valueOf(status);
            dh.setTrangThaiDonHang(trangThai);
            dh = this.donHangRepository.save(dh);

            res.Data = dh;
        }catch (Exception e){
            res.Code = -1;
            res.Message = "Có lỗi xảy ra, vui lòng thử lại";
        }

        return new ResponseEntity(res, HttpStatus.OK);
    }
}
