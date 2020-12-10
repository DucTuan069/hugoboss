package com.Boss.shipping.GHN;

import com.Boss.shipping.District;
import com.Boss.shipping.ShippingFee;
import com.Boss.shipping.ShippingService;
import com.Boss.shipping.GHTK.GHTKResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class GHNService extends ShippingService {

	private String apiLink = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
	private String accessToken = "72311a64-3564-11eb-93da-4612b8d25643";
	private final RestTemplate restTemplate;

	@Autowired
	public GHNService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public ShippingFee getFee(String from, String to) {
		ShippingFee se = new ShippingFee();

		District f = this.getDistrictFromName(from);
		District t = this.getDistrictFromName(to);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Host", this.apiLink);
		headers.set("Token", this.accessToken);

		final int fromDistrict = 1485; // Cau Giay
		final int serviceId = 53320; // Van chuyen nhanh
		final int codService = 100012; // Phi COD

//        // create a map for post parameters
//        Map<String, Object> data = new HashMap<>();
//        data.put("token", this.accessToken);
//        data.put("Weight", 500);
//        data.put("FromDistrictID", f.getDistrictID());
//        data.put("ToDistrictID", t.getDistrictID());
//        data.put("ServiceID", serviceId);
//
//        ObjectMapper mapper = new ObjectMapper();
		try {
//            String jsonData = mapper.writeValueAsString(data);
//            HttpEntity<String> entity = new HttpEntity<String>(jsonData, headers);
			String url = this.apiLink + "?service_id=" + serviceId + "&from_district_id=" + fromDistrict
					+ "&to_district_id=" + t.getDistrictID() + "&weight=500";
//			System.out.println(url);
			HttpEntity entity = new HttpEntity(headers);

			ResponseEntity<GHNResponse> response = this.restTemplate.exchange(url, HttpMethod.POST, entity,
					GHNResponse.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				se.setService("GHN");
				se.setFee(response.getBody().data.total);
			}
			return se;
		} catch (HttpClientErrorException hce) {
			hce.printStackTrace();
			se.setMessage(hce.getMessage());
		}

		return se;
	}
}
