package com.example.springboot.services;

import com.example.springboot.entity.Account;
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
    public Account saveExpert(Expert account) throws ExpertException {
        final String password = account.getPassword();
        final String email = account.getEmail();
        Validation validation = new Validation();
        if (!validation.validString(account.getFirstName())&& validation.validString(account.getLastName()))
            throw new ExpertException("wrong firstname or lastname !!");
        if (!validation.validPassword(password))
            throw new ExpertException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (!validation.validateEmail(email))
            throw new ExpertException("Email not valid");
        account.setInventory(0);
        return expertRepository.save(account);

    }

    @Override
    public Expert findByUsernameAndPassword(String username, String password) {
        return expertRepository.findByUsernameAndPassword(username, password);
    }
}
