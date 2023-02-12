package com.example.springboot.services;

import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.ExpertException;

public interface ExpertService {
    Expert saveExpert(Expert account) throws ExpertException;
    Expert findByUsernameAndPassword(String username, String password) throws ExpertException;

    Expert confirmExpert(Expert expert) throws ExpertException;

    Expert requestForNewJob(Expert expert, SubTasks sub);

    Expert changePassword(Expert expert , String newPassword);

    void removeSubServiceFromExpert(Expert expert , SubTasks sub);

}
