package com.example.springboot.controller;

import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.services.TasksServices;
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

    @PostMapping("/saveTasks")
    public Tasks saveService(@RequestBody Tasks service) throws TasksException {
        return services.saveTask(service);
    }

    @GetMapping("/getAlltasks")
    public List<Tasks> allTasks() {
        return services.allTasks();
    }

}
