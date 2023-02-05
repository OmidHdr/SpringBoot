package com.example.springboot.controller;

import com.example.springboot.entity.SubTasks;
import com.example.springboot.services.SubTaskServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubTasksControler {

    private final SubTaskServices subTaskServices;

    public SubTasksControler(SubTaskServices subTaskServices) {
        this.subTaskServices = subTaskServices;
    }

    @PostMapping("/saveSubTasks")
    public SubTasks saveSubTask(@RequestBody SubTasks sub){
        return subTaskServices.saveSubTask(sub);
    }

}
