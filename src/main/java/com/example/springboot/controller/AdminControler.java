package com.example.springboot.controller;

import com.example.springboot.entity.Admin;
import com.example.springboot.exeption.AdminException;
import com.example.springboot.repository.SearchRepository;
import com.example.springboot.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AdminControler {

    private final AdminService adminService;
    private final SearchRepository searchRepository;

    /*
    //section find
    @GetMapping("/search/{role}/{type}")
    public void search(@PathVariable String role , @PathVariable String type){
        final List<Customer> usersByCriteria = userRepository.findUsersByCriteria("omid", "omidian", "omid@gmail.com", "omid");
    }
     */
    // only for creating admin for first time
    @PostMapping("/admin")
    public String saveAdmin() throws AdminException {
        Admin admin = Admin.builder().username("root").password("root").build();
        return adminService.saveAdmin(admin);
    }

}
