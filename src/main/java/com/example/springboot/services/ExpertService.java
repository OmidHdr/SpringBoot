package com.example.springboot.services;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.ReguestJob;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;

public interface ExpertService {
    Expert saveExpert(Expert account) throws ExpertException, TasksException, SubTasksException;
    Expert findByUsernameAndPassword(String username, String password) throws ExpertException;

    Expert confirmExpert(Expert expert) throws ExpertException;

    Expert requestForNewJob(ReguestJob job) throws ExpertException, TasksException, SubTasksException;
//    Expert removeSubServiceFromExpert(Expert expert , SubTasks sub);

    Expert changePassword(ChangePassword changePassword) throws ExpertException;


}
