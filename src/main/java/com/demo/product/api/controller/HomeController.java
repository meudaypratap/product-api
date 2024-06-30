package com.demo.product.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Checkout api documentation at <a href='http://localhost:8080/swagger-ui/index.html'> http://localhost:8080/swagger-ui/index.html</a>");
    }
}
