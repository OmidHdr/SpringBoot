package com.example.springboot.controller;

import com.example.springboot.entity.Admin;
import com.example.springboot.services.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminControler {

    private final AdminService adminService;

    public AdminControler(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin")
    public Admin saveAdmin(@RequestBody Admin admin){
        return adminService.saveAdmin(admin);
    }

}
