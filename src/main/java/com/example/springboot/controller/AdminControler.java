package com.example.springboot.controller;

import com.example.springboot.entity.Admin;
import com.example.springboot.exeption.AdminException;
import com.example.springboot.services.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminControler {

    private final AdminService adminService;

    public AdminControler(AdminService adminService) {
        this.adminService = adminService;
    }

//    /*
    // only for creating admin for first time
    @PostMapping("/admin")
    public String saveAdmin(@RequestBody Admin admin) throws AdminException {
        return adminService.saveAdmin(admin);
    }
//     */

}
