package com.practice.boardproject.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {

    @GetMapping("/get")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("GET");
    }
}
