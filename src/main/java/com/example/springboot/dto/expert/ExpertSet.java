package com.example.springboot.dto.expert;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertSet {
    String firstName;
    String lastName;
    String email;
    String subTaskName;
    String username;
    String password;
}
