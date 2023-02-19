package com.example.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping(value ="/")
    public String hello(){
        return "Welcome to this Application !! ";
    }


}
