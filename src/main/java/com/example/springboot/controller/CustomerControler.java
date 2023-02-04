package com.example.springboot.controller;

import com.example.springboot.services.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerControler {

    private final CustomerService customerService;

    public CustomerControler(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @PostMapping("/customer")
//    public Customer saveCustomer(Customer customer){
//        return customerService.saveCustomer(customer);
//    }

}
