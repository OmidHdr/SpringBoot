package com.example.springboot.dto.login;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Login {
    private String username;
    private String password;
}
