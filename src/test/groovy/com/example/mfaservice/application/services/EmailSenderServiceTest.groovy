package com.example.mfaservice.application.services

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

class EmailSenderServiceTest extends Specification {

    def emailSender = Mock(JavaMailSender)
    def emailSenderService = new EmailSenderService(emailSender)

    def "sendEmail"() {
        given:
        def toEmail = "email@gmail.com"
        def body = "body"
        def message = new SimpleMailMessage();
        message.setFrom("mfaNexoExample@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Verification Code");
        message.setText(body);

        when:
        emailSenderService.sendEmail(toEmail, body)

        then:
        1 * emailSender.send(message)
    }

}
