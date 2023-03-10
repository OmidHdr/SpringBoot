package com.example.springboot.services;

import com.example.springboot.dto.subTask.SubTaskSave;
import com.example.springboot.dto.subTask.SubtaskEdit;
import com.example.springboot.dto.subTask.SubTaskDto;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.mapper.ProductMapper;
import com.example.springboot.repository.SubTasksRepository;
import com.example.springboot.repository.TasksRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public SubTasks saveSubTask(SubTaskSave sub) throws SubTasksException {
        if (sub.getName() == null || sub.getDescription() == null ||
                sub.getTaskName() == null || sub.getBasePrice() == null)
            throw new SubTasksException("didn't set task please set it and try again later");
        final Tasks byName = tasksRepository.findByName(sub.getTaskName());
        if (byName == null) {
            throw new SubTasksException("Tasks dose not exist please add tasks first");
        }

        try {
            final SubTasks subTask = ProductMapper.INSTANCE.dtosave(sub);
            subTask.setTask(byName);
            return repository.save(subTask);
        } catch (Exception e) {
            throw new SubTasksException("SubTasks already exist");
        }
    }

    //section all subtask
    @Override
    public List<SubTaskDto> allSubTasks() throws SubTasksException {
        final List<SubTasks> all = repository.findAll();
        if (all == null)
            throw new SubTasksException("there is No result");
        List<SubTaskDto> result = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            SubTaskDto subTasks = SubTaskDto.builder().name(all.get(i).getName())
                            .description(all.get(i).getDescription())
                            .basePrice(all.get(i).getBasePrice())
                            .id(all.get(i).getId()).build();
            result.add(subTasks);
        }
        return result;
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
    public void editSubTask(SubtaskEdit sub) throws SubTasksException {
        if (sub.getName() == null || sub.getNewName() == null || sub.getNewDescription() == null || sub.getNewBasePrice() == null)
            throw new SubTasksException("you should fill all of the items");
        final SubTasks byName = repository.findByName(sub.getName());
        if (byName == null)
            throw new SubTasksException("this Subtask dose not exist");
        byName.setName(sub.getNewName());
        byName.setDescription(sub.getNewDescription());
        byName.setBasePrice(sub.getNewBasePrice());
        repository.save(byName);
    }


}
