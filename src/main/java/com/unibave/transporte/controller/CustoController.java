package com.unibave.transporte.controller;

import com.unibave.transporte.dtos.Response;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/custos/frete")
public class CustoController {
        private static final String key = "AIzaSyA408XQGcwvcyZQ0DLZEPsWkZhDtcczTFE";
        @GetMapping
        public Response getGeoCode(@RequestParam String address_origin) {
                ResponseEntity<Response> response = new RestTemplate().getForEntity("https://maps.googleapis.com/maps/api/geocode/json?address=" + address_origin + "&key=" + key,
                        Response.class);

                Response coordinates = response.getBody();

                double lat = coordinates.getResult()[0].getGeometry().getLocation().getLat();

                double lng = coordinates.getResult()[0].getGeometry().getLocation().getLng();
                System.out.println("latitude" + lat);
                System.out.println("longitude" + lng);
                return response.getBody();

        }
}


