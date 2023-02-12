package com.example.springboot.services;

import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.SubTasksException;

import java.util.List;

public interface SubTaskServices {
    SubTasks saveSubTask(SubTasks sub) throws SubTasksException;
    List<SubTasks> allSubTasks();
}
