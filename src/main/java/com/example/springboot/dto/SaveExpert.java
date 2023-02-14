package com.example.springboot.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveExpert {
    String firstName;
    String lastName;
    String email;
    String username;
    String password;
    String subTaskName;
}
