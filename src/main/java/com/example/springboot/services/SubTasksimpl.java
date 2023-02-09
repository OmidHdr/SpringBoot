package com.example.springboot.services;

import com.example.springboot.entity.SubTasks;
import com.example.springboot.repository.SubTasksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTasksimpl implements SubTaskServices {

    private final SubTasksRepository repository;


    public SubTasksimpl(SubTasksRepository repository) {
        this.repository = repository;
    }


    @Override
    public SubTasks saveSubTask(SubTasks sub) {
        return repository.save(sub);
    }

    @Override
    public List<SubTasks> allSubTasks() {
        return repository.findAll();
    }


}
