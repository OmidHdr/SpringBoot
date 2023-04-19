package com.example.springboot.dto.customer;

import com.example.springboot.entity.Enum.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
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
