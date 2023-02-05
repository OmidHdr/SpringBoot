package com.example.springboot.services;

import com.example.springboot.entity.SubTasks;
import com.example.springboot.repository.SubTasksRepository;
import org.springframework.stereotype.Service;

@Service
public class SubTaskTasksimpl implements SubTaskServices {

    private final SubTasksRepository repository;


    public SubTaskTasksimpl(SubTasksRepository repository) {
        this.repository = repository;
    }


    @Override
    public SubTasks saveSubTask(SubTasks sub) {
        return repository.save(sub);
    }
}
