package com.example.springboot.services;

import com.example.springboot.dto.*;
import com.example.springboot.dto.expert.ExpertSet;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;

import java.util.List;

public interface ExpertService {
    Expert saveExpert(ExpertSet account) throws ExpertException, TasksException, SubTasksException;
    Expert findByUsernameAndPassword(String username, String password) throws ExpertException;

    void confirmExpert(Expert expert) throws ExpertException;

    Expert requestForNewJob(ReguestJob job) throws ExpertException, TasksException, SubTasksException;
//    Expert removeSubServiceFromExpert(Expert expert , SubTasks sub);

    void changePassword(ChangePassword changePassword) throws ExpertException;


    List<ExpertSet> showUnconfirmExpert() throws ExpertException;

    void removeExpertFromSubtask(RemoveExpertFromSubService remove) throws ExpertException, SubTasksException, TasksException;
}
