package com.example.springboot.services;

import com.example.springboot.entity.Account;
import com.example.springboot.exeption.CustomerException;

public interface CustomerService {
    Account saveCustomer(Account account) throws CustomerException;

    Account findByUsernameAndPassword(String username, String password);
}
