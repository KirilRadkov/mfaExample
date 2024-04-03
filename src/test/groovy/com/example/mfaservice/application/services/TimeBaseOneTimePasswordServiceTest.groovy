package com.example.mfaservice.application.services

import com.example.mfaservice.domain.VerificationCodeResult
import spock.lang.Specification

import java.time.LocalDateTime

class TimeBaseOneTimePasswordServiceTest extends Specification {

    def passwordService = Spy(TimeBaseOneTimePasswordService) {
        // stub a call on the same object
        getUuidString(_) >> "dc6817d3-d740-4d5c-b88e-b4087e314b40"
    }

    def "GenerateValidationCode"() {
        given:
        def email = "email@gmail.com"

        when:
        def result = passwordService.generateVerificationCode(email)

        then:
        result == "DC6817"
    }

    def "GettUuidString"() {
        given:
        def str = "dc6817d3-d740-4d5c-b88e-b4087e314b40"

        when:
        def result = passwordService.getUuidString(str)

        then:
        result == "dc6817d3-d740-4d5c-b88e-b4087e314b40"
    }

    def "ValidateVerificationCode: happy path"() {

        def expirationTimestamp = LocalDateTime.now().plusMinutes(3)
        def verificationCode = "DC6817"
        def savedValidationCode = "DC6817"

        when:
        def result = passwordService.validateVerificationCode(verificationCode, savedValidationCode, expirationTimestamp)

        then:
        result == VerificationCodeResult.SUCCESS
    }

    def "ValidateVerificationCode: verificationCode is null"() {

        def expirationTimestamp = LocalDateTime.now().plusMinutes(3)
        def verificationCode = null
        def savedValidationCode = "DC6817"

        when:
        def result = passwordService.validateVerificationCode(verificationCode, savedValidationCode, expirationTimestamp)

        then:
        result == VerificationCodeResult.ERROR
    }

    def "ValidateVerificationCode: savedValidationCode is null"() {

        def expirationTimestamp = LocalDateTime.now().plusMinutes(3)
        def verificationCode = "DC6817"
        def savedValidationCode = null

        when:
        def result = passwordService.validateVerificationCode(verificationCode, savedValidationCode, expirationTimestamp)

        then:
        result == VerificationCodeResult.ERROR
    }

    def "ValidateVerificationCode: verification code is not valid"() {

        def expirationTimestamp = LocalDateTime.now().plusMinutes(3)
        def verificationCode = "DC6817"
        def savedValidationCode = "ABCDEF"

        when:
        def result = passwordService.validateVerificationCode(verificationCode, savedValidationCode, expirationTimestamp)

        then:
        result == VerificationCodeResult.FAILURE
    }

    def "ValidateVerificationCode: verification code is expired"() {

        def expirationTimestamp = LocalDateTime.now().minusMinutes(3)
        def verificationCode = "DC6817"
        def savedValidationCode = "DC6817"

        when:
        def result = passwordService.validateVerificationCode(verificationCode, savedValidationCode, expirationTimestamp)

        then:
        result == VerificationCodeResult.FAILURE
    }
}
