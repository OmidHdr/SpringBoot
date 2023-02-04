package com.example.springboot.services;

import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Expert;

public interface AdminService {
    Admin saveAdmin(Admin admin);

    Admin getAdmin(String username, String password);

    Expert confirmExpert(Expert expert);
}
