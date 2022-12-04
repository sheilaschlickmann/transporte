package com.unibave.transporte.controller;

import com.unibave.transporte.dtos.Location;
import com.unibave.transporte.dtos.Response;
import com.unibave.transporte.dtos.ResponseDistance;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
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

        private static final String API_KEY = "AIzaSyA408XQGcwvcyZQ0DLZEPsWkZhDtcczTFE";
        private static final String BASE_URI = "https://maps.googleapis.com/maps/api";
        @GetMapping
        public ResponseDistance getGeoCode(@RequestParam String address_origin, String address_destiny) {
                ResponseEntity<Response> responseOrigin = new RestTemplate().getForEntity(BASE_URI + "/geocode/json?address=" + address_origin + "&key=" + API_KEY,
                        Response.class);

                Location coordinatesOrigin = responseOrigin.getBody().getResult()[0].getGeometry().getLocation();

                double latOrigin = coordinatesOrigin.getLat();
                double lngOrigin = coordinatesOrigin.getLng();

                System.out.println("latitude Origin " + latOrigin);
                System.out.println("longitude Origin " + lngOrigin);

                ResponseEntity<Response> responseDestiny = new RestTemplate().getForEntity(BASE_URI + "/geocode/json?address=" + address_destiny + "&key=" + API_KEY,
                        Response.class);

                Location coordinatesDestiny = responseDestiny.getBody().getResult()[0].getGeometry().getLocation();

                double latDestiny = coordinatesDestiny.getLat();
                double lngDestiny = coordinatesDestiny.getLng();

                System.out.println("latitude " + latDestiny);
                System.out.println("longitude " + lngDestiny);

                String originAddress = latOrigin + "," + lngOrigin;
                String destinationAddress = latDestiny + "," + lngDestiny;

                System.out.println("originAddress " + originAddress);
                System.out.println("destinationAddress " + destinationAddress);

                ResponseEntity<ResponseDistance> responseDistance = new RestTemplate().getForEntity(BASE_URI + "/distancematrix/json?origins=" + originAddress + "&destinations=" + destinationAddress +"&key=" + API_KEY,
                        ResponseDistance.class);

                int value = responseDistance.getBody().getRows()[0].getElements()[0].getDistance().getValue();

                System.out.println("value " + value);

                return responseDistance.getBody();

        }
}


