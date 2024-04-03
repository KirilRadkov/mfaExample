package com.example.mfaservice.adapter.out.db


import spock.lang.Specification

import java.time.LocalDateTime

class VerificationCodeDbAdapterTest extends Specification {

    def verificationCodeRepository = Mock(VerificationCodeRepository)
    def adapter = new VerificationCodeDbAdapter(verificationCodeRepository)

    def "FindByEmail"() {
        when:
        adapter.findByEmail("email")

        then:
        1 * verificationCodeRepository.findByEmail("email")
    }

    def "SaveVerificationCodeEntity"() {
        given:
        def entity = VerificationCodeEntity.builder()
                .verificationCode("ABC3EF")
                .email("email@gmail.com")
                .expirationTimestamp(LocalDateTime.now())
                .build()
        when:
        adapter.saveVerificationCodeEntity(entity)

        then:
        1 * verificationCodeRepository.save(entity)
    }

    def "DeleteExpiredVerificationCodes"() {
        given:
        def now = LocalDateTime.now()

        when:
        adapter.deleteExpiredVerificationCodes(now)

        then:
        1 * verificationCodeRepository.deleteVerificationCodeEntitiesByExpirationTimestampBefore(now)
    }
}
