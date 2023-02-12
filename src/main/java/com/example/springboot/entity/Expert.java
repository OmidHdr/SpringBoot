package com.example.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class Expert extends Account{

    Boolean status ;

    byte[] image;

    @JoinColumn(nullable = false)
    @ManyToOne
    Tasks tasks;

    @JoinColumn(nullable = false)
    @ManyToOne
    SubTasks subTasks;

}
