package com.example.mfaservice.adapter.in.scheduler;

import com.example.mfaservice.application.ports.ports.in.VerificationCodeDelitionUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@Service
public class DeleteExpiredVerificationCodesSchedulerCommand {

    private final VerificationCodeDelitionUseCase verificationCodeDelitionUseCase;

    /**
     * Deletes all expired verification Codese every 30 Minutes, that are older than 1 Hour
     */
    @Scheduled(fixedDelay = 1800000)
    public void execute(){
        verificationCodeDelitionUseCase.deleteExpiredVerificationCodes(LocalDateTime.now().minusHours(1));
    }


}