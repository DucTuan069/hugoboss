package com.Boss.users;

import com.Boss.ResponseAPI;
import com.Boss.shop.CuaHang;
import com.Boss.shop.CuaHangRepository;
import com.Boss.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/qly-tai-khoan")
public class QlyTaiKhoanController {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity getDanhSachTaiKhoan(){
        TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaiKhoan u = user.getUser();
        LoaiTaiKhoan role = u.getLoaiTaiKhoan();

        List<TaiKhoan> ds = new ArrayList<>();

        if(role == LoaiTaiKhoan.QuanLy){
            ds = u.getCuaHang().getDanhSachNhanVien();
        }else

        if(role == LoaiTaiKhoan.Admin){
            ds = this.taiKhoanRepository.findAll();
        }

        ResponseAPI response = new ResponseAPI();
        response.Data = ds;
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity taoTaiKhoan(@RequestBody TaiKhoanRequestData req){
        ResponseAPI response = new ResponseAPI();
        TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaiKhoan u = user.getUser();
        LoaiTaiKhoan role = u.getLoaiTaiKhoan();

        CuaHang shop = u.getCuaHang();

        if(role == LoaiTaiKhoan.Admin){
            int shopId = req.getIdCuaHang();
            shop = this.cuaHangRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Không tìm thấy cửa hàng có id = " + shopId));
        }

        TaiKhoan tk = this.taiKhoanRepository.findByTenDangNhap(req.getTenDangNhap()).orElse(null);
        if(tk != null){
            response.Code = -1;
            response.Message = "Tên đăng nhập đã tồn tại";
            return new ResponseEntity(response, HttpStatus.OK);
        }

        tk = new TaiKhoan();
        tk.setTenDangNhap(req.getTenDangNhap());
        tk.setSdt(req.getSdt());
        tk.setMatKhau(this.passwordEncoder.encode(req.getMatKhau()));
        tk.setLoaiTaiKhoan(req.getLoaiTaiKhoan());
        tk.setHoTen(req.getHoTen());
        tk.setGioiTinh(req.getGioiTinh());
        tk.setDiaChi(req.getDiaChi());
        tk.setCuaHang(shop);

        tk = this.taiKhoanRepository.save(tk);


        response.Data = tk;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity editTaiKhoan(@PathVariable int userId, @RequestBody TaiKhoanRequestData req){
        TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaiKhoan u = user.getUser();
        LoaiTaiKhoan role = u.getLoaiTaiKhoan();

        CuaHang shop = u.getCuaHang();

        if(role == LoaiTaiKhoan.Admin){
            int shopId = req.getIdCuaHang();
            shop = this.cuaHangRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Không tìm thấy cửa hàng có id = " + shopId));
        }

        TaiKhoan tk = this.taiKhoanRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản có id = " + userId));
        tk.setSdt(req.getSdt());
        tk.setTenDangNhap(req.getTenDangNhap());
        tk.setLoaiTaiKhoan(req.getLoaiTaiKhoan());
        if(!req.getMatKhau().isEmpty()) tk.setMatKhau(this.passwordEncoder.encode(req.getMatKhau()));
        tk.setHoTen(req.getHoTen());
        tk.setDiaChi(req.getDiaChi());
        tk.setGioiTinh(req.getGioiTinh());
        tk.setCuaHang(shop);

        tk = this.taiKhoanRepository.save(tk);

        ResponseAPI response = new ResponseAPI();
        response.Data = tk;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity xoaTaiKhoan(@PathVariable int userId){
        ResponseAPI response = new ResponseAPI();
        TaiKhoanDetail user = (TaiKhoanDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaiKhoan u = user.getUser();

        if(u.getId() == userId){
            response.Code = -1;
            response.Message = "Bạn không có quyền thực hiện hành động này!";
            return new ResponseEntity(response, HttpStatus.OK);
        }

        LoaiTaiKhoan role = u.getLoaiTaiKhoan();

        CuaHang shop = u.getCuaHang();
        TaiKhoan tk = this.taiKhoanRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản có id = " + userId));

        if(role == LoaiTaiKhoan.QuanLy && tk.getCuaHang().getId() != shop.getId()){
            response.Code = -1;
            response.Message = "Bạn không có quyền thực hiện hành động này!";
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }

        this.taiKhoanRepository.delete(tk);

        response.Data = 1;

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
