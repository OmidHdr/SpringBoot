package com.example.springboot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorMessage {

    HttpStatus status;
    String massage;

    public ErrorMessage(String massage) {
        this.massage = massage;
    }
}
