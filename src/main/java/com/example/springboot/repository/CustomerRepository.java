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
    Customer findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM customer WHERE ? = ?",nativeQuery = true)
    List<Customer> findByItem(@Param("search") String search ,@Param("item") String item);
//    List<Customer> findByItem(@Param("item") String item);

    Optional<Customer> findByUsername(String username);
}
