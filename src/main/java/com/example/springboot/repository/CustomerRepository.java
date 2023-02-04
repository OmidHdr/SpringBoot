package com.example.springboot.repository;

import com.example.springboot.entity.Account;
import com.example.springboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByUsernameAndPassword(String username, String password);
}
