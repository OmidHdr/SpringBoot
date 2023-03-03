package com.example.springboot.services;

import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.AdminException;
import com.example.springboot.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceimpl implements AdminService{

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String saveAdmin(Admin admin) throws AdminException {
        if (admin.getUsername() == null || admin.getPassword() == null)
            throw new AdminException("please give me username or password");
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(UserRole.ROLE_ADMIN);
        adminRepository.save(admin);
        return "register successfully";
    }

    @Override
    public Admin getAdmin(String username) throws AdminException {
        final Optional<Admin> byUsername = adminRepository.findByUsername(username);
        if (byUsername.isEmpty())
            throw new AdminException("could not find admin");
        return byUsername.get();
    }



}
