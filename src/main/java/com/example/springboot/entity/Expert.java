package com.example.springboot.entity;

import com.example.springboot.entity.Enum.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Expert{
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

    byte[] image;

    @ManyToOne
    Tasks services;

    @ManyToOne
    SubTasks subServices;

}
