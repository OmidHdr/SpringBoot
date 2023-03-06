package com.example.springboot.services;

import com.example.springboot.dto.*;
import com.example.springboot.dto.expert.ExpertSet;
import com.example.springboot.dto.expert.dtoExpert;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;

import java.util.List;

public interface ExpertService {
    Expert saveExpert(ExpertSet account) throws ExpertException, TasksException, SubTasksException;
    Expert findByUsernameAndPassword(String username, String password) throws ExpertException;

    void confirmExpert(String username) throws ExpertException;

    Expert requestForNewJob(ReguestJob job,Expert expert) throws ExpertException, TasksException, SubTasksException;
//    Expert removeSubServiceFromExpert(Expert expert , SubTasks sub);

    void changePassword(String password,Expert expert) throws ExpertException;


    List<dtoExpert> showUnconfirmExpert() throws ExpertException;

    void removeExpertFromSubtask(String subtaskName , String usernameExpert) throws ExpertException, SubTasksException, TasksException;
    Expert findById(Long id) throws ExpertException;

    List<Expert> findBySubTask(SubTasks subtask) throws ExpertException;
}
