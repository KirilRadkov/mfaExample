package com.example.mfaservice.domain

import spock.lang.Specification

import java.time.LocalDateTime

class VerificationCodeTest extends Specification {

    def now = LocalDateTime.now()
    def expTimestampt = LocalDateTime.now().plusMinutes(5)
    def verificationCode = new VerificationCode(1, "code", "email", now, expTimestampt);

    def "MapToEntity"() {

        when:
        def resultEntity = verificationCode.mapToEntity()

        then:
        resultEntity.getVerificationCode() == "code"
        resultEntity.getEmail() == "email"
        resultEntity.getExpirationTimestamp() == expTimestampt
    }
}
