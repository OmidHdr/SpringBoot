package com.example.springboot.controller;

import com.example.springboot.dto.*;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.services.ExpertService;
import com.example.springboot.services.ExpertServiceimpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpertControler {

    private final ExpertService expertService;

    public ExpertControler(ExpertServiceimpl expertService) {
        this.expertService = expertService;
    }

    //section register Expert
    @PostMapping("/registerExpert")
    public void saveExpert(@RequestBody GetExpert account) throws ExpertException, SubTasksException, TasksException {
        expertService.saveExpert(account);
    }

    //section login Expert
    @PostMapping("/loginExpert")
    public Expert getExpert(@RequestBody Expert expert) throws ExpertException {
        return expertService.findByUsernameAndPassword(expert.getUsername(),expert.getPassword());
    }
    //section confirm expert
    @PostMapping("/confirmExpert")
    public void confirmExpert(@RequestBody Expert expert) throws ExpertException {
        expertService.confirmExpert(expert);
    }
    //section show unConfirm
    @GetMapping("/showUnconfirmExpert")
    public List<GetExpert> showUnconfirmExpert() throws ExpertException {
        return expertService.showUnconfirmExpert();
    }
    //section change Pass
    @PostMapping("/changePasswordExpert")
    public void changePasswordExpert(@RequestBody ChangePassword changePassword) throws ExpertException {
        expertService.changePassword(changePassword);
    }
    @PostMapping("/requestJob")
    public Expert requestNewJob(@RequestBody ReguestJob job) throws ExpertException, SubTasksException, TasksException {
        return expertService.requestForNewJob(job);
    }
    @PostMapping("removeExpertFromSubtask")
    public void removeExpertFromSubtask(@RequestBody RemoveExpertFromSubService remove) throws ExpertException, SubTasksException, TasksException {
        expertService.removeExpertFromSubtask(remove);
    }
}
