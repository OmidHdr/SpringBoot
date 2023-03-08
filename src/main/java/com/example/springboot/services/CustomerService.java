package com.example.springboot.services;

import com.example.springboot.entity.Customer;
import com.example.springboot.exeption.CustomerException;

public interface CustomerService {
    Customer saveCustomer(Customer account) throws CustomerException;

    Customer findByUsernameAndPassword(String username, String password) throws CustomerException;

    Customer changePassword(Customer customer,String password) throws CustomerException;

    Customer save(Customer customer);

    Customer verify(String token) throws CustomerException;
}
