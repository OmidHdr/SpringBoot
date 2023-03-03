package com.example.springboot.controller;

import com.example.springboot.dto.task.TaskEdit;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.services.TasksServices;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TasksControler {

    private final TasksServices services;

    public TasksControler(TasksServices services) {
        this.services = services;
    }

    //section save
    @PostMapping("/saveTasks")
    @PreAuthorize("hasRole('ADMIN')")
    public Tasks saveService(@RequestBody Tasks service) throws TasksException {
        return services.saveTask(service);
    }

    //section get all task
    @GetMapping("/getAlltasks")
    @PreAuthorize("hasAnyRole()")
    public List<Tasks> allTasks() {
        return services.allTasks();
    }

    //section edit task
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editTask")
    public Tasks editTask(@RequestBody TaskEdit task) throws TasksException {
        return services.editTask(task);
    }
}
