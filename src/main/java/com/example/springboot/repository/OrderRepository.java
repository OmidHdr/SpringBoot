package com.example.springboot.repository;

import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.entity.Orders;
import com.example.springboot.entity.SubTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> findByJobStatusAndSubTasks(JobStatus status , SubTasks subTasks);
}
