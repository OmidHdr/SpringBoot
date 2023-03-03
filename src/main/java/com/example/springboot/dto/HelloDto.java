package com.example.springboot.dto;

import io.micrometer.common.lang.NonNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelloDto {
    @NonNull
    String name;
    @NonNull
    String captchaResponse;
}
