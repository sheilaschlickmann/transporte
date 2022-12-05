package com.unibave.transporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TransporteApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransporteApplication.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("gelatina"));
    }

}
