package com.gamma.digitalsignservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloWorldProtectedController {

    @GetMapping
    public ResponseEntity<String> helloProtected() {
        return new ResponseEntity<>("Hello, you're accessing a protected service", HttpStatus.OK);
    }
}
