package com.example.springboot.repository;

import com.example.springboot.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert,Long> {
    Expert findByUsernameAndPassword(String username, String password);
}
