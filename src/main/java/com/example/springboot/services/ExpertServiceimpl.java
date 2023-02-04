package com.example.springboot.services;

import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.repository.ExpertRepository;
import com.example.springboot.validation.Validation;
import org.springframework.stereotype.Service;

@Service
public class ExpertServiceimpl implements ExpertService {

    private final ExpertRepository expertRepository;

    public ExpertServiceimpl(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    @Override
    public Expert saveExpert(Expert expert) throws ExpertException {
        final String password = expert.getPassword();
        final String email = expert.getEmail();
        Validation validation = new Validation();
        if (!validation.validPassword(password))
            throw new ExpertException("password should have at a capital Letter and a minimal Letter and 8 character");
        if (!validation.validateEmail(email))
            throw new ExpertException("Email not valid");
        return expertRepository.save(expert);

    }
}
