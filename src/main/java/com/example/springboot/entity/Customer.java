package com.example.springboot.entity;

import com.example.springboot.entity.Enum.UserRole;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Customer extends Account{
    public Customer(String firstName, String lastName, String email, String username, String password, UserRole userRole) {
        super(firstName, lastName, email, username, password, userRole);
    }

    public Customer() {

    }
}
