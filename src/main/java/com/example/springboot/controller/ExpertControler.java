package com.example.springboot.controller;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.ReguestJob;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.services.ExpertServiceimpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpertControler {

    private final ExpertServiceimpl expertService;

    public ExpertControler(ExpertServiceimpl expertService) {
        this.expertService = expertService;
    }

    //section register Expert
    @PostMapping("/registerExpert")
    public Expert saveExpert(@RequestBody Expert account) throws ExpertException, SubTasksException, TasksException {
        return expertService.saveExpert(account);
    }

    //section login Expert
    @GetMapping("/loginExpert")
    public Expert getExpert(@RequestBody Expert expert) throws ExpertException {
        return expertService.findByUsernameAndPassword(expert.getUsername(),expert.getPassword());
    }
    //section confirm expert
    @PostMapping("/confirmExpert")
    public Expert confirmExpert(@RequestBody Expert expert) throws ExpertException {
        return expertService.confirmExpert(expert);
    }
    //section show unConfirm
    @GetMapping("/showUnconfirmExpert")
    public List<Expert> showUnconfirmExpert() throws ExpertException {
        return expertService.showUnconfirmExpert();
    }
    //section change Pass
    @PostMapping("/changePasswordExpert")
    public Expert changePasswordExpert(@RequestBody ChangePassword changePassword) throws ExpertException {
        return expertService.changePassword(changePassword);
    }
    @PostMapping("/requestJob")
    public Expert requestNewJob(@RequestBody ReguestJob job) throws ExpertException, SubTasksException, TasksException {
        return expertService.requestForNewJob(job);
    }
}
