package com.example.springboot.controller;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.payment.PayWallet;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.OfferException;
import com.example.springboot.exeption.OrderException;
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
    public Customer saveCustomer(@RequestBody Customer customer) throws CustomerException {
        return customerService.saveCustomer(customer);
    }

    @PostMapping("/login")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Customer loginCustomer(@RequestBody Customer customer) throws CustomerException {
        Customer cus = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cus;
//        return customerService.findByUsernameAndPassword(customer.getUsername(),customer.getPassword());
    }

    @PreAuthorize("hasAnyRole('')")
    @PostMapping("/changePasswordCustomer")
    public Customer changePassword(@RequestBody ChangePassword changePassword) throws CustomerException {
        return customerService.changePassword(changePassword);
    }

    @PostMapping("/find/{find}/{item}")
    public Customer findCustomer(@PathVariable(value = "find") String find , @PathVariable(value = "item") String item) throws CustomerException {
        return customerService.findCustomer(find, item);
    }

}
