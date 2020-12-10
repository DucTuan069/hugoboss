package com.Boss.shipping.GHN;

import com.Boss.ResponseAPI;
import com.Boss.shipping.ShippingFee;
import com.Boss.shop.CuaHang;
import com.Boss.shop.CuaHangRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/shipping/ghn")
public class GHNController {

    @Autowired
    private GHNService ghnService;

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @GetMapping("fee")
    public ResponseEntity getGHNShippingFee(@RequestParam int shopId, @RequestParam String toDistrict){
        CuaHang shop = this.cuaHangRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Không tìm thấy cửa hàng có id = " + shopId));
        String diaChiShop = shop.getDiaChi();
        String[] diaChiPart = diaChiShop.split(",");
        String fromDistrict = diaChiShop;
        toDistrict = toDistrict.toUpperCase();

        ShippingFee fee = this.ghnService.getFee(fromDistrict, toDistrict);

        ResponseAPI response = new ResponseAPI();
        response.Data = fee;

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
