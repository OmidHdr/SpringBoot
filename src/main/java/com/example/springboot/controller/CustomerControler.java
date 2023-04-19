package com.example.springboot.controller;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.customer.dtoCustomer;
import com.example.springboot.entity.Customer;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.mapper.ProductMapper;
import com.example.springboot.services.CustomerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerControler {

    private final CustomerService customerService;

    public CustomerControler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public String online(@ModelAttribute Customer customer) throws CustomerException {
        System.out.println(customer.toString());
        customerService.saveCustomer(customer);
        return "ok";
    }

    @PostMapping("/login")
    public dtoCustomer loginCustomer() {
        Customer cus = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ProductMapper.INSTANCE.customerToDto(cus);
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('CUSTOMER')")
    public dtoCustomer changePassword(@RequestBody ChangePassword changePassword) throws CustomerException {
        Customer cus = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Customer customer = customerService.changePassword(cus, changePassword.getPassword());
        return ProductMapper.INSTANCE.customerToDto(customer);
    }

//    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/verify")
    public dtoCustomer verifyCustomer(@RequestParam("token") String token) throws CustomerException {
        final Customer cus = customerService.verify(token);
        return ProductMapper.INSTANCE.customerToDto(cus);
    }


}
