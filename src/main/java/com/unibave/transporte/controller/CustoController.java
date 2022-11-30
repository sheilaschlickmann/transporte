package com.unibave.transporte.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/custos/frete")
public class CustoController {

@GetMapping
public String getGeocode(@RequestParam String latlng) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String key = "AIzaSyA408XQGcwvcyZQ0DLZEPsWkZhDtcczTFE";

        Request request = new Request.Builder()
        .url("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latlng + "&key=" + key)
        .get()
        .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        return responseBody.string();
        }
}


