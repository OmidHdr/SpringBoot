package com.example.springboot.controller;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.expert.ExpertSet;
import com.example.springboot.dto.ReguestJob;
import com.example.springboot.dto.RemoveExpertFromSubService;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.services.ExpertService;
import com.example.springboot.services.ExpertServiceimpl;
import com.example.springboot.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ExpertControler {

    private final ExpertService expertService;
    private final OrderService orderService;

    public ExpertControler(ExpertServiceimpl expertService, OrderService orderService) {
        this.expertService = expertService;
        this.orderService = orderService;
    }

    //section register Expert
    @PostMapping("/registerExpert")
    public void saveExpert(@RequestBody ExpertSet account) throws ExpertException, SubTasksException, TasksException {
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
    public List<ExpertSet> showUnconfirmExpert() throws ExpertException {
        return expertService.showUnconfirmExpert();
    }
    //section change Pass
    @PostMapping("/changePasswordExpert")
    public void changePasswordExpert(@RequestBody ChangePassword changePassword) throws ExpertException {
        expertService.changePassword(changePassword);
    }
    //section reguest new job
    @PostMapping("/requestJob")
    public Expert requestNewJob(@RequestBody ReguestJob job) throws ExpertException, SubTasksException, TasksException {
        return expertService.requestForNewJob(job);
    }
    //section remove expert in sub
    @PostMapping("/removeExpertFromSubtask")
    public void removeExpertFromSubtask(@RequestBody RemoveExpertFromSubService remove) throws ExpertException, SubTasksException, TasksException {
        expertService.removeExpertFromSubtask(remove);
    }

}
