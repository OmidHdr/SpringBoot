package com.example.springboot.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Entity
@SuperBuilder
public class Customer extends Account{

}
