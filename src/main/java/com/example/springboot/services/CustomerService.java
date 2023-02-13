package com.example.springboot.services;

import com.example.springboot.entity.Account;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.CustomerException;

public interface CustomerService {
    Customer saveCustomer(Customer account) throws CustomerException;

    Customer findByUsernameAndPassword(String username, String password) throws CustomerException;

    Customer changePassword(String username, String password, String newPassword) throws CustomerException;

}
