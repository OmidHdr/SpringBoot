package com.example.springboot.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetExpert {
    String firstName;
    String lastName;
    String email;
    String subTaskName;
    String username;
    String password;
}
