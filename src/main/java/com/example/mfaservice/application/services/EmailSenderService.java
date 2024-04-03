package com.example.mfaservice.application.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender emailSender;

    /**
     * Creates and sends an Email with the 6-digits verification code
     * @param toEmail
     * @param body
     */
    public void sendEmail(String toEmail, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mfaNexoExample@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Verification Code");
        message.setText(body);

        emailSender.send(message);

        log.info("Message sent successfully");
    }
}
