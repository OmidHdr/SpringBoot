package com.example.springboot;

import com.example.springboot.services.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Scanner;

@RequiredArgsConstructor
@SpringBootApplication
public class Application {

    private final EmailSender emailSender;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
