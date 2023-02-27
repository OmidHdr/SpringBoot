package com.example.springboot.controller;

import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.dto.payment.PaymentDto;
import com.example.springboot.entity.Customer;
import com.example.springboot.exeption.*;
import com.example.springboot.services.OrderService;
import com.example.springboot.services.ValidateCaptcha;
import com.example.springboot.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final OrderService orderService;
    @PostMapping("/peyment/online")
    public String test(@ModelAttribute PaymentDto dto) throws PaymentException {
        System.out.println(dto.toString());
        Validation.validPayment(dto);
        return "ok";
    }

    @PostMapping("/payment/wallet/{id}")
    public OrderShow payWithWallet(@PathVariable(value = "id") Long id, @RequestBody Customer account) throws OrderException, CustomerException, OfferException, ExpertException {
        return orderService.payWithWallet(id,account);
    }

}
