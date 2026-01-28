package com.spuzakov.astro.astrobusinessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AstroBusinessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AstroBusinessServiceApplication.class, args);
    }

}
