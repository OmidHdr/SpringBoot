package com.example.springboot.services;

import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.ExpertException;

public interface ExpertService {
    Expert saveExpert(Expert expert) throws ExpertException;
}
