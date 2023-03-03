package com.example.springboot.controller;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.customer.dtoCustomer;
import com.example.springboot.dto.login.Login;
import com.example.springboot.dto.payment.PayWallet;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.OfferException;
import com.example.springboot.exeption.OrderException;
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
    public dtoCustomer saveCustomer(@RequestBody Customer customer) throws CustomerException {
        final Customer cus = customerService.saveCustomer(customer);
        return ProductMapper.INSTANCE.customerToDto(cus);
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

    @PostMapping("/find/{find}/{item}")
    public Customer findCustomer(@PathVariable(value = "find") String find, @PathVariable(value = "item") String item){
        return customerService.findCustomer(find, item);
    }

}
