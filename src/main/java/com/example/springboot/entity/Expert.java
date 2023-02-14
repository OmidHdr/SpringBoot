package com.example.springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    @ManyToMany
    List<SubTasks> subTasks;

}
