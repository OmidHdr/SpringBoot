package com.example.springboot.repository;

import com.example.springboot.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByUsernameAndPassword(String username, String password);
    Optional<Admin> findByUsername(String username);
}
