package com.Boss.user;

import com.Boss.ResponseAPI;
import com.Boss.shop.CuaHang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/tai-khoan")
public class TaiKhoanController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @GetMapping("chi-tiet")
    public ResponseEntity getThongTinChiTiet(){
        TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseAPI response = new ResponseAPI();
        response.Data = user.getUser();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity taoKhachHang(@RequestBody TaiKhoanRequestData req){
        TaiKhoan tk = new TaiKhoan();
        tk.setTenDangNhap(req.getTenDangNhap());
        tk.setSdt(req.getSdt());
        tk.setMatKhau(this.passwordEncoder.encode(req.getMatKhau()));
        tk.setLoaiTaiKhoan(LoaiTaiKhoan.KhachHang);
        tk.setHoTen(req.getHoTen());
        tk.setGioiTinh(req.getGioiTinh());
        tk.setDiaChi(req.getDiaChi());
        tk.setCuaHang(null);

        tk = this.taiKhoanRepository.save(tk);

        ResponseAPI response = new ResponseAPI();
        response.Data = tk;

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
