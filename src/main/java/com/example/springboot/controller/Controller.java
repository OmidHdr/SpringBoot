package com.example.springboot.controller;

import com.example.springboot.services.ExpertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final ExpertService expertService;

    public Controller(ExpertService expertService) {
        this.expertService = expertService;
    }

    @GetMapping(value ="/")
    public String hello(){
        return "Hello world !! ";
    }


}
