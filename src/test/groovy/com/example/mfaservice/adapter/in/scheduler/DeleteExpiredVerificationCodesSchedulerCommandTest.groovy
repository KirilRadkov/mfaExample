package com.example.mfaservice.adapter.in.scheduler

import com.example.mfaservice.application.ports.ports.in.VerificationCodeDelitionUseCase
import spock.lang.Specification

import java.time.LocalDateTime

class DeleteExpiredVerificationCodesSchedulerCommandTest extends Specification {

    def verificationCodeDelitionUseCase = Mock(VerificationCodeDelitionUseCase)
    def scheduler = new DeleteExpiredVerificationCodesSchedulerCommand(verificationCodeDelitionUseCase)

    def "Execute"() {
        when:
        scheduler.execute()

        then:
        1 * verificationCodeDelitionUseCase.deleteExpiredVerificationCodes(*_)
    }
}
