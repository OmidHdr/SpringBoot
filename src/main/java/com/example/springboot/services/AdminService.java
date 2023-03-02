package com.example.springboot.services;

import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Account;
import com.example.springboot.exeption.AdminException;

public interface AdminService {
    String saveAdmin(Admin admin) throws AdminException;

    Admin getAdmin(String username, String password);

}
