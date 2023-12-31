package com.aboredswe.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class ProtectedResourceController {

    @GetMapping
    public String printHi(){
        return "Hello";
    }
}
