package com.example.springboot.controller;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.payment.PayWallet;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.OfferException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.services.CustomerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerControler {

    private final CustomerService customerService;

    public CustomerControler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/registerCustomer")
    public Customer saveCustomer(@RequestBody Customer customer) throws CustomerException {
        return customerService.saveCustomer(customer);
    }
    @PostMapping("/loginCustomer")
    public Customer loginCustomer(@RequestBody Customer customer) throws CustomerException {
        return customerService.findByUsernameAndPassword(customer.getUsername(),customer.getPassword());
    }
    @PostMapping("/changePasswordCustomer")
    public Customer changePassword(@RequestBody ChangePassword changePassword) throws CustomerException {
        return customerService.changePassword(changePassword);
    }

//    section pay
    @PostMapping("/payment/wallet/{id}")
    public void paywithWallet(@PathVariable(value = "id") Long id , @RequestBody PayWallet payWallet) throws CustomerException, OfferException, OrderException {
        customerService.payment(id, payWallet);
    }

}
