package com.example.springboot.services;

import com.example.springboot.entity.captcha.CaptchaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static java.lang.System.out;

@RequiredArgsConstructor
@Service
public class ValidCaptcha {
    private final RestTemplate template;
    @Value("https://google.com/recaptcha/api/siteverify")
    String recaptchaEndpoint;
    @Value("6LfKvpokAAAAAMtXy5RqB6tikqJDK4IrWqLK4Nue")
    String recaptchaSecret;

    public boolean validateCaptcha(final String captchaResponse) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", recaptchaSecret);
        params.add("response", captchaResponse);

        CaptchaResponse apiResponse = null;
        try {
            apiResponse = template.postForObject(recaptchaEndpoint, params, CaptchaResponse.class);
        } catch (final RestClientException e) {
            out.println("Some exception occurred while binding to the recaptcha endpoint.");
        }

        if (Objects.nonNull(apiResponse) && apiResponse.isSuccess()) {
            out.println("Captcha API response");
            return true;
        } else {
            return false;
        }
    }
    
}
