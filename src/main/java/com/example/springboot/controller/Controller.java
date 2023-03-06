package com.example.springboot.controller;

import com.example.springboot.dto.SearchCustomer;
import com.example.springboot.dto.SearchExpert;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class Controller {

    private final SearchRepository search;

    @GetMapping("/home")
    public String hello(){
        return "Welcome to this Application !! ";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findCustomer")
    public List<Customer> findCustomer(@RequestBody SearchCustomer request){
        return search.findCustomerByCriteria(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findExpert")
    public List<Expert> findExpert(@RequestBody SearchExpert request) throws ExpertException, SubTasksException {
        return search.findExpertByCriteria(request);
    }

}
