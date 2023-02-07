package com.example.springboot.services;

import com.example.springboot.entity.Admin;
import com.example.springboot.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class AdminServiceimplTest {

    @Mock
    private AdminRepository adminRepository;
    @InjectMocks
    private AdminServiceimpl adminService;

    @Test
    void saveAdmin() {
        Admin admin = Admin.builder().username("root").password("root").build();
        BDDMockito.given(adminRepository.save(admin)).willReturn(admin);
        Admin admin1 = adminService.saveAdmin(admin);
        assertEquals("root",admin1.getUsername());
    }
}