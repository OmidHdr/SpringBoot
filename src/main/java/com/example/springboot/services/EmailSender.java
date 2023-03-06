package com.example.springboot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    public String sendEmail(String toEmail , String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("omidoneof1@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        return "Mail sent successfully";
    }

}
