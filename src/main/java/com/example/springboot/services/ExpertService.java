package com.example.springboot.services;

import com.example.springboot.entity.Account;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.ExpertException;

public interface ExpertService {
    Account saveExpert(Expert account) throws ExpertException;
    Expert findByUsernameAndPassword(String username, String password);
}
