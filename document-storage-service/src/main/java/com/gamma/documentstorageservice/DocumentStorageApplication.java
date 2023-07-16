package com.gamma.documentstorageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.gamma.model")
public class DocumentStorageApplication {
    public static void main(String[] args) {
        System.out.println("Hello world from document service!");
        SpringApplication.run(DocumentStorageApplication.class, args);
    }
}