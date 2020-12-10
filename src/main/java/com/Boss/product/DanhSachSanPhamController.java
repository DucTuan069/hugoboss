package com.Boss.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Boss.ResponseAPI;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/products")
public class DanhSachSanPhamController {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping("")
    public ResponseEntity getDSSanPham(){
        List<SanPham> sps = this.sanPhamRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<SanPhamResponseData> resData = new ArrayList<>();

        for(int i=0;i<sps.size();i++){
            SanPhamResponseData sprd = new SanPhamResponseData(sps.get(i));
            resData.add(sprd);
        }

        ResponseAPI response = new ResponseAPI();
        response.Data = resData;

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
