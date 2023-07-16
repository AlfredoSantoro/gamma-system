package com.gamma.digitalsignservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.gamma.model")
public class DigitalSignApplication {
    public static void main(String[] args) {
        System.out.println("Hello world from digital sign service!");
        SpringApplication.run(DigitalSignApplication.class, args);
    }
}