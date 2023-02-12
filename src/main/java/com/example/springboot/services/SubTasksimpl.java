package com.example.springboot.services;

import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.repository.SubTasksRepository;
import com.example.springboot.repository.TasksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTasksimpl implements SubTaskServices {

    private final SubTasksRepository repository;
    private final TasksRepository tasksRepository;

    public SubTasksimpl(SubTasksRepository repository, TasksRepository tasksRepository) {
        this.repository = repository;
        this.tasksRepository = tasksRepository;
    }


    @Override
    public SubTasks saveSubTask(SubTasks sub) throws SubTasksException {
        if (sub.getTask().getName() == null)
            throw new SubTasksException("didn't set task please set it and try again later");
        final Tasks byName = tasksRepository.findByName(sub.getTask().getName());
        if (byName == null) {
            throw new SubTasksException("Tasks dose not exist please add tasks first");
        }
        try {
            sub.setTask(byName);
            return repository.save(sub);
        } catch (Exception e) {
            throw new SubTasksException("SubTasks already exist");
        }
    }

    @Override
    public List<SubTasks> allSubTasks() {
        return repository.findAll();
    }


}
