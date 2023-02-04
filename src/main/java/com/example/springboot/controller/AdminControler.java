package com.example.springboot.controller;

import com.example.springboot.entity.Expert;
import com.example.springboot.services.ExpertService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminControler {

//    private final AdminService adminService;
    private final ExpertService expertService;

    public AdminControler(ExpertService expertService) {
        this.expertService = expertService;
    }

    //section confirm Expert
    @PostMapping("/expert")
    public Expert saveExpert(@RequestBody Expert expert){
        return expertService.saveExpert(expert);
    }

//    @PostMapping("/admin")
//    public Admin saveAdmin(@RequestBody Admin admin){
//        return adminService.saveAdmin(admin);
//    }
//
//    @GetMapping("/admin/{username}/{password}")
//    public Admin getAdmin(@PathVariable("username") String username,
//                          @PathVariable("password") String password){
//       return adminService.getAdmin(username, password);
//    }
//
//    public Expert confirmExpert(@RequestBody Expert expert){
//        return adminService.confirmExpert(expert);
//    }

}
