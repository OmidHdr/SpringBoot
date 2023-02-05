package com.example.springboot.controller;

import com.example.springboot.entity.Services;
import com.example.springboot.services.ServiceServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceControler {

    private final ServiceServices services;

    public ServiceControler(ServiceServices services) {
        this.services = services;
    }

    @PostMapping("/saveService")
    public Services saveService(@RequestBody Services service){
        return services.saveService(service);
    }

}
