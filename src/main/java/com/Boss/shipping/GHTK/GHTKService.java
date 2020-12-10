package com.Boss.shipping.GHTK;

import com.Boss.shipping.District;
import com.Boss.shipping.ShippingFee;
import com.Boss.shipping.ShippingService;
import com.Boss.shipping.GHN.GHNResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class GHTKService extends ShippingService {

    private String apiLink = "https://services.giaohangtietkiem.vn/services/shipment/fee";
    private String accessToken = "F1163E675B33e370c45F80D630892BD9F14aE19c";
    private final RestTemplate restTemplate;

    @Autowired
    public GHTKService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ShippingFee getFee(String from, String to) {
        ShippingFee se = new ShippingFee();

        District f = this.getDistrictFromName(from);
        District t = this.getDistrictFromName(to);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Host", this.apiLink);
        headers.set("Token", this.accessToken);

        // create a map for post parameters
//        Map<String, Object> data = new HashMap<>();
//        data.put("pick_province", f.getProvinceName());
//        data.put("pick_district", f.getDistrictName());
//        data.put("province", t.getProvinceName());
//        data.put("district", t.getDistrictName());
//        data.put("weight", 500); //Gram

//        ObjectMapper mapper = new ObjectMapper();
        try {
            String url = this.apiLink + "?pick_province="+f.getProvinceName()+
                                        "&pick_district="+f.getDistrictName()+
                                        "&province="+t.getProvinceName()+
                                        "&district="+t.getDistrictName()+
                                        "&weight=500";
            
            HttpEntity entity = new HttpEntity(headers);
            
            ResponseEntity<GHTKResponse> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, GHTKResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                se.setService("GHTK");
                se.setFee(response.getBody().fee.fee);
            }

            return se;
        } catch (HttpClientErrorException hce){
            hce.printStackTrace();
            se.setMessage(hce.getMessage());
        }

        return se;
    }
}
