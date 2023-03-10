package com.example.springboot.controller;

import com.example.springboot.dto.subTask.SubTaskSave;
import com.example.springboot.dto.subTask.SubtaskEdit;
import com.example.springboot.dto.subTask.SubTaskDto;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.services.SubTaskServices;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public SubTasks saveSubTask(@RequestBody SubTaskSave sub) throws SubTasksException {
        return subTaskServices.saveSubTask(sub);
    }

    @GetMapping("/getAllSubtasks")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','EXPERT')")
    public List<SubTaskDto> getAllSubTasks() throws SubTasksException {
        return subTaskServices.allSubTasks();
    }

    @PostMapping("/editSubtask")
    @PreAuthorize("hasRole('ADMIN')")
    public void editSubTask(@RequestBody SubtaskEdit sub) throws SubTasksException {
        subTaskServices.editSubTask(sub);
    }
}
