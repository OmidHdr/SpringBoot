package com.example.springboot.services;

import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Account;

public interface AdminService {
    Admin saveAdmin(Admin admin);

    Admin getAdmin(String username, String password);

}
