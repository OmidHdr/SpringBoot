package com.example.springboot.dto.expert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertSet {
    String firstName;
    String lastName;
    String email;
    String subTaskName;
    String username;
    String password;
    String token;
}
