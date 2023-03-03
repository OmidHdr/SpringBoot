package com.example.springboot.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayWallet {
    @NotNull
    String username;
    @NotNull
    String password;
    
}
