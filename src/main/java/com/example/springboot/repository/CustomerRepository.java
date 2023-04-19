package com.example.springboot.repository;

import com.example.springboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByUsername(String username);

    @Query("update Customer c set c.status = true where c.username = ?1")
    Customer verifyCustomer(String username);

    Optional<Customer> findByToken(String token);

}
