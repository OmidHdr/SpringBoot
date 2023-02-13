package com.example.springboot.services;

import com.example.springboot.dto.TaskEdit;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.TasksException;

import java.util.List;

public interface TasksServices {
    Tasks saveTask(Tasks service) throws TasksException;
    List<Tasks> allTasks();

    Tasks findByName(String name) throws TasksException;

    Tasks editTask(TaskEdit task) throws TasksException;
}
