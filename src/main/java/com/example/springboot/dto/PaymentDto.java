package com.example.springboot.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {

        @Pattern(regexp = "^[0-9]{16}")
        String cardNumber;

        @Pattern(regexp = "^[0-9]{3,4}")
        String cvv2;
}
