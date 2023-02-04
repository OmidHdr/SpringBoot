package com.example.springboot.services;

import com.example.springboot.entity.Expert;
import com.example.springboot.repository.ExpertRepository;
import org.springframework.stereotype.Service;

@Service
public class ExpertServiceimpl implements ExpertService{

    private final ExpertRepository expertRepository;

    public ExpertServiceimpl(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    @Override
    public Expert saveExpert(Expert expert) {
        return expertRepository.save(expert);
    }
}
