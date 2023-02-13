package com.example.springboot.controller;

import com.example.springboot.dto.SubtaskEdit;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.services.SubTaskServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubTasksControler {

    private final SubTaskServices subTaskServices;

    public SubTasksControler(SubTaskServices subTaskServices) {
        this.subTaskServices = subTaskServices;
    }

    @PostMapping("/saveSubTasks")
    public SubTasks saveSubTask(@RequestBody SubTasks sub) throws SubTasksException {
        return subTaskServices.saveSubTask(sub);
    }

    @GetMapping("/getAllsubtasks")
    public List<SubTasks> getSubTasks(){
        return subTaskServices.allSubTasks();
    }

    @PostMapping("/editSubtask")
    public SubTasks editSubTask(@RequestBody SubtaskEdit sub) throws SubTasksException {
        return subTaskServices.editSubTask(sub);
    }
}
