package com.example.springboot.services;

import com.example.springboot.dto.task.TaskEdit;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.repository.TasksRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TasksServiceimpl implements TasksServices {

    private final TasksRepository repository;

    public TasksServiceimpl(TasksRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tasks saveTask(Tasks service) throws TasksException {
        try {
            return repository.save(service);
        } catch (Exception e) {
            throw new TasksException("Service Already exist");
        }

    }

    //section all tasks
    @Override
    public List<Tasks> allTasks() {
        return repository.findAll();
    }

    //section find by name
    @Override
    public Tasks findByName(String name) throws TasksException {
        final Tasks byName = repository.findByName(name);
        if (byName == null)
            throw new TasksException("Wrong task name");
        return byName;
    }

    //section edit Task
    @Override
    public Tasks editTask(TaskEdit task) throws TasksException {
        if (task.getNewName() == null || task.getName() == null)
            throw new TasksException("you should fill all of the items");
        Tasks byName = repository.findByName(task.getName());
        if (byName == null)
            throw new TasksException("this task dose not exist");
        byName.setName(task.getNewName());
        return repository.save(byName);
    }

}
