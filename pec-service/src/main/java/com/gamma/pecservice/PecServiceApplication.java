package com.gamma.pecservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.gamma.model")
@EnableJpaRepositories({"com.gamma.repository"})

@ComponentScan({"com.gamma.auth", "com.gamma.pecservice"})
public class PecServiceApplication {

    public static void main(String[] args) {
        System.out.println("Hello world from pec service!");
        SpringApplication.run(PecServiceApplication.class, args);
    }
}