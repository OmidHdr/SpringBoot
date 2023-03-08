package com.example.springboot.controller;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.expert.ExpertSet;
import com.example.springboot.dto.ReguestJob;
import com.example.springboot.dto.RemoveExpertFromSubService;
import com.example.springboot.dto.expert.dtoExpert;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.mapper.ProductMapper;
import com.example.springboot.services.ExpertService;
import com.example.springboot.services.ExpertServiceimpl;
import com.example.springboot.services.OrderService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/expert")
public class ExpertControler {

    private final ExpertService expertService;
    private final OrderService orderService;

    public ExpertControler(ExpertServiceimpl expertService, OrderService orderService) {
        this.expertService = expertService;
        this.orderService = orderService;
    }

    //section register Expert
    @PostMapping("/register")
    public String saveExpert(@RequestBody ExpertSet account) throws ExpertException, SubTasksException, TasksException {
        expertService.saveExpert(account);
        return "Expert register successfully";
    }

    //section login Expert
    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping("/login")
    public String getExpert() {
        return "Expert Logged in successfully";
    }

    //section confirm expert
    @PostMapping("/confirm/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public String confirmExpert(@PathVariable(value = "username") String username) throws ExpertException {
        expertService.confirmExpert(username);
        return "The expert Confirm Successfully";
    }

    //section show unConfirm
    @GetMapping("/showUnconfirm")
    @PreAuthorize("hasRole('ADMIN')")
    public List<dtoExpert> showUnconfirmExpert() throws ExpertException {
        final List<dtoExpert> dtoExperts = expertService.showUnconfirmExpert();
        return dtoExperts;
    }

    //section change Pass
    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('EXPERT')")
    public String changePasswordExpert(@RequestBody ChangePassword changePassword) throws ExpertException {
        Expert expert = (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expertService.changePassword(changePassword.getPassword(), expert);
        return "password changed";
    }

    //section reguest new job
    @PostMapping("/requestJob")
    @PreAuthorize("hasRole('EXPERT')")
    public dtoExpert requestNewJob(@RequestBody ReguestJob job) throws ExpertException, SubTasksException, TasksException {
        Expert expert = (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Expert returedExpert = expertService.requestForNewJob(job, expert);
        return ProductMapper.INSTANCE.expertToDto(returedExpert);
    }

    //section remove expert in sub
    @PostMapping("/removeFromSubtask")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeExpertFromSubtask(@RequestBody RemoveExpertFromSubService remove) throws ExpertException, SubTasksException, TasksException {
        expertService.removeExpertFromSubtask(remove.getSubtaskName(), remove.getUsername());
        return "removed Successfully";
    }

    //section verify
    @GetMapping("/verify")
    public dtoExpert verifyCustomer(@RequestParam("token") String token) throws ExpertException {
        final Expert expert = expertService.verify(token);
        return ProductMapper.INSTANCE.expertToDto(expert);
    }
}
