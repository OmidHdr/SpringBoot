package com.example.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class Expert extends Account{

    Boolean status = false;

    byte[] image;

    @ManyToOne
    Tasks services;

    @ManyToOne
    SubTasks subServices;

}
