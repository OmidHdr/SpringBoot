package com.example.springboot.controller;

import com.example.springboot.dto.HelloDto;
import com.example.springboot.dto.HelloResponseDto;
import com.example.springboot.dto.expert.ForbiddenException;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Customer;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.OfferException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.services.OrderService;
import com.example.springboot.services.ValidateCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class Payment {

    private final ValidateCaptcha service;
    private final OrderService orderService;
//    @PostMapping("/test")
//    public String test(@ModelAttribute PaymentDto dto) {
//        System.out.println(dto.toString());
//        return "ok";
//    }

    @PostMapping("/test")
    @ResponseStatus(code = HttpStatus.OK)
    public HelloResponseDto welcome(@RequestBody final HelloDto dto)
            throws ForbiddenException {
        final boolean isValidCaptcha = service.validateCaptcha(dto.getCaptchaResponse());
        if (!isValidCaptcha) {
            throw new ForbiddenException("INVALID_CAPTCHA");
        }
        return new HelloResponseDto("Greetings " + dto.getName());
    }

    @PostMapping("/payment/wallet/{id}")
    public OrderShow payWithWallet(@PathVariable(value = "id") Long id, @RequestBody Customer account) throws OrderException, CustomerException, OfferException, ExpertException {
        return orderService.payWithWallet(id,account);
    }

}
