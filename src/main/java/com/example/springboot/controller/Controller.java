package com.example.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/test")
public class Controller {

    @GetMapping("/home")
    public String hello(){
        return "Welcome to this Application !! ";
    }


}
