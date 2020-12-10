package com.Boss.shop;

import com.Boss.ResponseAPI;
import com.Boss.danhmuc.DanhMuc;
import com.Boss.user.LoaiTaiKhoan;
import com.Boss.user.TaiKhoan;
import com.Boss.user.TaiKhoanDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/cua-hang")
public class CuaHangController {

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @GetMapping("")
    public ResponseEntity getDanhSachCuaHang(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CuaHang> shops = new ArrayList<>();

        if(principal instanceof TaiKhoanDetail){
            TaiKhoanDetail user = (TaiKhoanDetail) principal;
            TaiKhoan u = user.getUser();

//            if(u.getLoaiTaiKhoan() == LoaiTaiKhoan.Admin){
//                shops = cuaHangRepository.findAll();
//            }else{
//                CuaHang s = u.getCuaHang();
//                shops.add(s);
//            }

            shops = cuaHangRepository.findAll();
            
        }else{
            shops = cuaHangRepository.findAll();
        }

        ResponseAPI response = new ResponseAPI();
        response.Data = shops;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity taoCuaHang(@RequestBody CuaHangRequest req){
        CuaHang shop = new CuaHang();
        shop.setDiaChi(req.getDiaChi());
        shop.setSdt(req.getSdt());
        shop.setTenCuaHang(req.getTenCuaHang());

        shop = this.cuaHangRepository.save(shop);

        ResponseAPI response = new ResponseAPI();
        response.Data = shop;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("{shopId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity editCuaHang(@PathVariable int shopId, @RequestBody CuaHangRequest req){
        CuaHang shop = this.cuaHangRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Không tìm thấy cửa hàng có id = " + shopId));
        shop.setSdt(req.getSdt());
        shop.setDiaChi(req.getDiaChi());
        shop.setTenCuaHang(req.getTenCuaHang());

        shop = this.cuaHangRepository.save(shop);

        ResponseAPI response = new ResponseAPI();
        response.Data = shop;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("{cuaHangId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity xoaCuaHang(@PathVariable int cuaHangId){
        CuaHang shop = this.cuaHangRepository.findById(cuaHangId).orElseThrow(() -> new RuntimeException("Không tìm thấy cửa hàng có id = " + cuaHangId));
        this.cuaHangRepository.delete(shop);

        ResponseAPI response = new ResponseAPI();
        response.Data = 1;

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
