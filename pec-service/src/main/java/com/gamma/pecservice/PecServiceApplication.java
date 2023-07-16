package com.gamma.pecservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.gamma.model")
public class PecServiceApplication {

    public static void main(String[] args) {
        System.out.println("Hello world from pec service!");
        SpringApplication.run(PecServiceApplication.class, args);
    }
}