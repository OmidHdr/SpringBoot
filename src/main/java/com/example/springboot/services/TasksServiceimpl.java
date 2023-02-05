package com.example.springboot.services;

import com.example.springboot.entity.Tasks;
import com.example.springboot.repository.TasksRepository;
import org.springframework.stereotype.Service;

@Service
public class TasksServiceimpl implements TasksServices {

    private final TasksRepository repository;

    public TasksServiceimpl(TasksRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tasks saveTask(Tasks service) {
        return repository.save(service);
    }
}
