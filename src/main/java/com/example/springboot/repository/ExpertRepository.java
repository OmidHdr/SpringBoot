package com.example.springboot.repository;

import com.example.springboot.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends JpaRepository<Expert,Long> {

    Expert findByUsernameAndPassword(String username, String password);

    Expert findByUsername(String username);

    List<Expert> findAllByStatus(Boolean status);

}
