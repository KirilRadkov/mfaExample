package com.example.mfaservice.application.services

import com.example.mfaservice.application.ports.ports.out.VerificationCodePort
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

import java.time.LocalDateTime

class VerificationCodeDeletionServiceTest extends Specification {

    def verificationCodePort = Mock(VerificationCodePort)
    def deletionService = new VerificationCodeDeletionService(verificationCodePort)

    def "DeleteExpiredVerificationCodes"() {
        given:
        def now = LocalDateTime.now()

        when:
        deletionService.deleteExpiredVerificationCodes(now)

        then:
        1 * verificationCodePort.deleteExpiredVerificationCodes(now)
    }
}
