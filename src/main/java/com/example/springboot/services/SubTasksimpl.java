package com.example.springboot.services;

import com.example.springboot.dto.SubtaskEdit;
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

    //section save
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

    //section all subtask
    @Override
    public List<SubTasks> allSubTasks() {
        return repository.findAll();
    }

    //section find by name
    @Override
    public SubTasks findByName(String name) throws SubTasksException {
        final SubTasks byName = repository.findByName(name);
        if (byName == null)
            throw new SubTasksException("wrong subtask name");
        return byName;

    }

    //section edit Subtask
    @Override
    public SubTasks editSubTask(SubtaskEdit sub) throws SubTasksException {
        if (sub.getName() == null || sub.getNewName() == null || sub.getNewDescription() == null || sub.getNewBasePrice() == null)
            throw new SubTasksException("you should fill all of the items");
        final SubTasks byName = repository.findByName(sub.getName());
        if (byName == null)
            throw new SubTasksException("this Subtask dose not exist");
        byName.setName(sub.getNewName());
        byName.setDescription(sub.getNewDescription());
        byName.setBasePrice(sub.getNewBasePrice());
        return repository.save(byName);
    }


}
