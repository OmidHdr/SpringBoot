package com.example.springboot.dto.customer;

import com.example.springboot.entity.Enum.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class dtoCustomer {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Long inventory;

}
