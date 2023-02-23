package com.example.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Time {
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3])$")
    int hour;
    @Pattern(regexp = "^[0-5][0-9]$")
    int minute;
}
