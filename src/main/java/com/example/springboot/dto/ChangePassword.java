package com.example.springboot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePassword {

    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String newPassword;

}
