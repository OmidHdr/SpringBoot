package com.example.springboot.repository;

import com.example.springboot.entity.SubTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTasksRepository extends JpaRepository<SubTasks,Long> {
    SubTasks findByName(String name);
}
