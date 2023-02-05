package com.example.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Expert extends Account{

    byte[] image;

    @ManyToMany
    Set<Tasks> services;

    @ManyToMany
    Set<SubTasks> subServices;

}
