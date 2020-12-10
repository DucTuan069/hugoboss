package com.Boss.ncc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.Boss.ResponseAPI;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/ncc")
public class NhaCungCapController {
    @Autowired
    private NhaCungCapRepository nccRepository;

    @GetMapping("")
    public ResponseEntity getDanhSachNhaCungCap(){
        List<NhaCungCap> nccs = this.nccRepository.findAll();

        ResponseAPI response = new ResponseAPI();
        response.Data = nccs;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity taoNhaCungCap(@RequestBody NhaCungCapRequestData req){
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC(req.getMaNCC());
        ncc.setTenNCC(req.getTenNCC());

        ncc = this.nccRepository.save(ncc);

        ResponseAPI response = new ResponseAPI();
        response.Data = ncc;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("{nccId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity editNhaCungCap(@PathVariable int nccId, @RequestBody NhaCungCapRequestData req){
        NhaCungCap ncc = this.nccRepository.findById(nccId).orElseThrow(() -> new RuntimeException("Không tìm thấy nhà cung cấp có id = " + nccId));
        ncc.setMaNCC(req.getMaNCC());
        ncc.setTenNCC(req.getTenNCC());

        ncc = this.nccRepository.save(ncc);

        ResponseAPI response = new ResponseAPI();
        response.Data = ncc;

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("{nccId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('QUANLY')")
    public ResponseEntity editNhaCungCap(@PathVariable int nccId){
        NhaCungCap ncc = this.nccRepository.findById(nccId).orElseThrow(() -> new RuntimeException("Không tìm thấy nhà cung cấp có id = " + nccId));
        this.nccRepository.delete(ncc);

        ResponseAPI response = new ResponseAPI();
        response.Data = 1;

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
