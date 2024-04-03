package com.example.mfaservice.application.services

import com.example.mfaservice.adapter.out.db.VerificationCodeEntity
import com.example.mfaservice.application.ports.ports.out.VerificationCodePort
import com.example.mfaservice.domain.VerificationCodeResult
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

import java.time.LocalDateTime

class VerificationCodeValidationServiceTest extends Specification {

    def timeBaseOneTimePasswordService = Mock(TimeBaseOneTimePasswordService)
    def verificationCodePort = Mock(VerificationCodePort)
    def validationService = new VerificationCodeValidationService(timeBaseOneTimePasswordService, verificationCodePort)

    def "ValidateVerificationCode: Entity found"() {

        given:
        def expirationTimestampt = LocalDateTime.now().plusMinutes(5)
        def verificationCode = "ABC3EF"
        def email = "email@gmail.com"
        verificationCodePort.findByEmail(_ as String) >> Optional.of(VerificationCodeEntity.builder()
                .id(1)
                .verificationCode("ABC3EF")
                .email("email@gmail.com")
                .created(LocalDateTime.now())
                .expirationTimestamp(expirationTimestampt)
                .build())

        when:
        validationService.validateVerificationCode(email, verificationCode)

        then:
        1 * timeBaseOneTimePasswordService.validateVerificationCode(verificationCode, verificationCode, expirationTimestampt)
    }

    def "ValidateVerificationCode: Entity not found"() {
        given:
        def verificationCode = "ABC3EF"
        def email = "email@gmail.com"
        verificationCodePort.findByEmail(_ as String) >> Optional.empty()

        when:
        def result = validationService.validateVerificationCode(email, verificationCode)

        then:
        0 * timeBaseOneTimePasswordService.validateVerificationCode(*_)
        result == VerificationCodeResult.ERROR
    }

}
