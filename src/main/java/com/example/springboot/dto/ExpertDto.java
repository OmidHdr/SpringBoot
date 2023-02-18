package com.example.springboot.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertDto {

    String firstName;
    String lastName;
    String email;
//    String subTaskName;
    String username;
    String password;

}
