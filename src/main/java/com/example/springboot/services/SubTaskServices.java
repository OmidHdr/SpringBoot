package com.example.springboot.services;

import com.example.springboot.entity.SubTasks;

import java.util.List;

public interface SubTaskServices {
    SubTasks saveSubTask(SubTasks sub);
    List<SubTasks> allSubTasks();
}
