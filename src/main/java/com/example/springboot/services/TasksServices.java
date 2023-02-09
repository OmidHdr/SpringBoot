package com.example.springboot.services;

import com.example.springboot.entity.Tasks;

import java.util.List;

public interface TasksServices {
    Tasks saveTask(Tasks service);
    List<Tasks> allTasks();
}
