package com.example.springboot.repository;

import com.example.springboot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Account,Long> {
    Account findByUsernameAndPassword(String username, String password);
}
