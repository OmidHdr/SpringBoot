package com.example.springboot.services;

import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Expert;
import com.example.springboot.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceimpl implements AdminService{

    private AdminRepository adminRepository;

    public AdminServiceimpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdmin(String username, String password) {
        return null;
    }

    @Override
    public Expert confirmExpert(Expert expert) {
        return null;
    }

    

}
