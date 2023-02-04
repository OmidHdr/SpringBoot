package com.example.springboot.services;

import com.example.springboot.entity.Account;
import com.example.springboot.exeption.ExpertException;

public interface ExpertService {
    Account saveExpert(Account account) throws ExpertException;
}
