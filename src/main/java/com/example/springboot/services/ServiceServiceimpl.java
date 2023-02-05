package com.example.springboot.services;

import com.example.springboot.entity.Services;
import com.example.springboot.repository.ServiceRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceServiceimpl implements ServiceServices {

    private final ServiceRepository repository;

    public ServiceServiceimpl(ServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Services saveService(Services service) {
        return repository.save(service);
    }
}
