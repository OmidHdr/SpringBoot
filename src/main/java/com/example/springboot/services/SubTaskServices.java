package com.example.springboot.services;

import com.example.springboot.dto.subTask.SubtaskEdit;
import com.example.springboot.dto.subTask.SubTaskDto;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.SubTasksException;

import java.util.List;

public interface SubTaskServices {
    SubTasks saveSubTask(SubTasks sub) throws SubTasksException;
    List<SubTaskDto> allSubTasks() throws SubTasksException;

    SubTasks findByName(String name) throws SubTasksException;

    void editSubTask(SubtaskEdit sub) throws SubTasksException;
}
