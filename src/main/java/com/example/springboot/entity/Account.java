package com.example.springboot.entity;

import com.example.springboot.entity.Enum.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
@SuperBuilder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
//    @NotBlank(message = "please complete first name and try again")
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
