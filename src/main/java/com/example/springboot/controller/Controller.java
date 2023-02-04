package com.example.springboot.controller;

import com.example.springboot.entity.Account;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.services.CustomerService;
import com.example.springboot.services.ExpertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final ExpertService expertService;
    private final CustomerService customerService;

    public Controller(ExpertService expertService, CustomerService customerService) {
        this.expertService = expertService;
        this.customerService = customerService;
    }

    @GetMapping(value ="/")
    public String hello(){
        return "Welcome to this Application !! ";
    }

    //section register Expert
    @PostMapping("/registerExpert")
    public Account saveExpert(@RequestBody Expert account) throws ExpertException {
        return expertService.saveExpert(account);
    }

    //section register Customer
    @PostMapping("/registerCustomer")
    public Account saveCustomer(@RequestBody Customer account) throws CustomerException {
        return customerService.saveCustomer(account);
    }

    //section login Expert
    @GetMapping("/loginExpert")
    public Expert getExpert(@RequestBody Expert account) {
        return expertService.findByUsernameAndPassword(account.getUsername(),account.getPassword());
    }

    //section login Customer
    @GetMapping("/loginCustomer")
    public Customer getCustomer(@RequestBody Customer account){
        return customerService.findByUsernameAndPassword(account.getUsername(),account.getPassword());
    }

}
