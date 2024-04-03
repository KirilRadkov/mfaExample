package com.example.mfaservice.application.services

import com.example.mfaservice.adapter.out.db.VerificationCodeEntity
import com.example.mfaservice.application.ports.ports.out.VerificationCodePort
import spock.lang.Specification

import java.time.LocalDateTime

class VerificationCodeCreationServiceTest extends Specification {

    def emailSenderService = Mock(EmailSenderService)
    def timeBaseOneTimePasswordService = Mock(TimeBaseOneTimePasswordService)
    def verificationCodePort = Mock(VerificationCodePort)

    def creationService = new VerificationCodeCreationService(emailSenderService, timeBaseOneTimePasswordService, verificationCodePort)

    def "GenerateAndSendVerificationCodeEmail: verification Code found in DB"() {
        given:
        def email = "email@gmail.com"

        verificationCodePort.findByEmail(_ as String) >> Optional.of(VerificationCodeEntity.builder()
                .id(1)
                .verificationCode("ABC3EF")
                .email("email@gmail.com")
                .created(LocalDateTime.now())
                .expirationTimestamp(LocalDateTime.now())
                .build())

        when:
        creationService.generateAndSendVerificationCodeEmail(email)

        then:
        1 * verificationCodePort.saveVerificationCodeEntity(*_)
        1 * emailSenderService.sendEmail(*_)
    }

    def "GenerateAndSendVerificationCodeEmail: verification Code not found in DB"() {
        given:
        def email = "email@gmail.com"

        verificationCodePort.findByEmail(_ as String) >> Optional.empty()

        when:
        creationService.generateAndSendVerificationCodeEmail(email)

        then:
        1 * verificationCodePort.saveVerificationCodeEntity(*_)
        1 * emailSenderService.sendEmail(*_)
    }

    def "GenerateVerificationCode"() {
        given:
        def email = "email@gmail.com"

        when:
        creationService.generateVerificationCode(email)

        then:
        1 * timeBaseOneTimePasswordService.generateVerificationCode(email)
    }

    def "SendVerificationCodeEmail"() {
        def email = "email@gmail.com"
        def verificationCode = "ABVDFD"

        when:
        creationService.sendVerificationCodeEmail(email, verificationCode)

        then:
        1 * emailSenderService.sendEmail(email, _)
    }

    def "CreateEmailBody"() {
        def verificationCode = "ABVDFD"

        when:
        def result = creationService.createEmailBody(verificationCode)

        then:
        result == "Your verification code is: ABVDFD This code is valid only 5 minutes!"
    }

    def "CreateVerificationCodeEntity"() {
        def email = "email@gmail.com"
        def verificationCode = "ABVDFD"

        when:
        def result = creationService.createVerificationCodeEntity(email, verificationCode)

        then:
        result.getVerificationCode() == verificationCode
        result.getEmail() == email
    }
}
