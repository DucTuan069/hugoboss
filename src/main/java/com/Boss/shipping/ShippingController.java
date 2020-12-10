package com.Boss.shipping;

import com.Boss.ResponseAPI;
import com.Boss.shipping.GHN.GHNService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    @Autowired
    private GHNService ghnService;

    @GetMapping("districts")
    public ResponseEntity getDistricts(){
        List<District> districts = this.ghnService.getDistricts();

        ResponseAPI response = new ResponseAPI();
        response.Data = districts;

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
