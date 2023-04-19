package com.example.springboot.controller;

import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.dto.payment.PaymentDto;
import com.example.springboot.entity.Customer;
import com.example.springboot.exeption.*;
import com.example.springboot.services.OrderService;
import com.example.springboot.utills.Utills;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/payment")
@RestController
public class PaymentController {

    private final OrderService orderService;
    @PostMapping("/online")
    public String online(@ModelAttribute PaymentDto dto) throws PaymentException {
        System.out.println(dto.toString());
        Utills.validPayment(dto);
        return "ok";
    }

    @PostMapping("/wallet/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderShow payWithWallet(@PathVariable(value = "id") Long id) throws OrderException, CustomerException, OfferException, ExpertException, AdminException {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.payWithWallet(id,customer);
    }

}
