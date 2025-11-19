package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Fruteira API funcionando!";
    }
    
    @GetMapping("/status")
    public String status() {
        return "Sistema Online";
    }
}