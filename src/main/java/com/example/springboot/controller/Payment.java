package com.example.springboot.controller;

import com.example.springboot.dto.payment.PaymentDto;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Payment {

    @PostMapping("/test")
    public String test(@ModelAttribute PaymentDto dto) {
        System.out.println(dto.toString());
        return "ok";
    }

}
