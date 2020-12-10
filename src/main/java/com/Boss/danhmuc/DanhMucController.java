package com.Boss.danhmuc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.Boss.ResponseAPI;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/danh-muc")
public class DanhMucController {

    @Autowired
    private DanhMucRepository danhMucRepository;

    @GetMapping("")
    public ResponseEntity getDanhSachDanhMuc(){
        List<DanhMuc> dsDanhMuc = this.danhMucRepository.findAll();

        ResponseAPI response = new ResponseAPI();
        response.Data = dsDanhMuc;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity taoDanhMuc(@RequestBody DanhMucRequestData req){
        DanhMuc dm = new DanhMuc();
        dm.setMaDanhMuc(req.getMaDanhMuc());
        dm.setTenDanhMuc(req.getTenDanhMuc());

        dm = this.danhMucRepository.save(dm);

        ResponseAPI response = new ResponseAPI();
        response.Data = dm;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("{danhMucId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity editDanhMuc(@PathVariable int danhMucId, @RequestBody DanhMucRequestData req){
        DanhMuc dm = this.danhMucRepository.findById(danhMucId).orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục có id = " + danhMucId));
        dm.setMaDanhMuc(req.getMaDanhMuc());
        dm.setTenDanhMuc(req.getTenDanhMuc());

        dm = this.danhMucRepository.save(dm);

        ResponseAPI response = new ResponseAPI();
        response.Data = dm;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("{danhMucId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity xoaDanhMuc(@PathVariable int danhMucId){
        DanhMuc dm = this.danhMucRepository.findById(danhMucId).orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục có id = " + danhMucId));
        this.danhMucRepository.delete(dm);

        ResponseAPI response = new ResponseAPI();
        response.Data = 1;

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
