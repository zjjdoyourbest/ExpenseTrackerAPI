package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class testApi {
    @GetMapping("/hello")
    public ResponseEntity<?> hello(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return ResponseEntity.ok("Hello, " + username);
    }
}
