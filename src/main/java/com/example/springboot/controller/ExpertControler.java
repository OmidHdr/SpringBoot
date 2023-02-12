package com.example.springboot.controller;

import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.services.ExpertServiceimpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpertControler {

    private final ExpertServiceimpl expertService;

    public ExpertControler(ExpertServiceimpl expertService) {
        this.expertService = expertService;
    }

    //section register Expert
    @PostMapping("/registerExpert")
    public Expert saveExpert(@RequestBody Expert account) throws ExpertException {
        return expertService.saveExpert(account);
    }

    //section login Expert
    @GetMapping("/loginExpert")
    public Expert getExpert(@RequestBody Expert account) throws ExpertException {
        return expertService.findByUsernameAndPassword(account.getUsername(),account.getPassword());
    }
    @PostMapping("/confirmExpert")
    public Expert confirmExpert(@RequestBody Expert expert) throws ExpertException {
        return expertService.confirmExpert(expert);
    }
    @GetMapping("/showUnconfirmExpert")
    public List<Expert> showUnconfirmExpert(){
        return expertService.showUnconfirmExpert();
    }
}
