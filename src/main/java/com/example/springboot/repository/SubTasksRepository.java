package com.example.springboot.repository;

import com.example.springboot.entity.SubTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTasksRepository extends JpaRepository<SubTasks,Long> {
    SubTasks findByName(String name);
//    @Query(value = "select * from sub_tasks",nativeQuery = true)
//    List<SubTasks> findEverything();
}
