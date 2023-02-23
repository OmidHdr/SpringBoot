package com.example.springboot.services;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.payment.PayWallet;
import com.example.springboot.entity.Customer;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.OfferException;
import com.example.springboot.exeption.OrderException;

public interface CustomerService {
    Customer saveCustomer(Customer account) throws CustomerException;

    Customer findByUsernameAndPassword(String username, String password) throws CustomerException;

    Customer changePassword(ChangePassword changePassword) throws CustomerException;

    Customer save(Customer customer);
}
