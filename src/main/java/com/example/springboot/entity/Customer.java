package com.example.springboot.entity;

import com.example.springboot.entity.Enum.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    String firstName;
    String lastName;
    @Column(unique = true)
    String email;
    LocalDate date = LocalDate.now();
    @Column(unique = true)
    String username;
    @Column(nullable = false)
    String password;
    @Enumerated(EnumType.STRING)
    UserRole userRole;
    Integer inventory;

}
