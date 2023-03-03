package com.example.springboot.entity.captcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CaptchaResponse {
    boolean success;
    LocalDateTime challenge_ts;
    String hostname;
    @JsonProperty("error-codes")
    List<String> errorCodes;
}
