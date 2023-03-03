package com.example.springboot.repository;

import com.example.springboot.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert,Long> {

    Expert findByUsernameAndPassword(String username, String password);

    Optional<Expert> findByUsername(String username);

    List<Expert> findAllByStatus(Boolean status);

}
