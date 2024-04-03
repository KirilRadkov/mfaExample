package com.example.mfaservice.application.services;

import com.example.mfaservice.application.ports.ports.in.VerificationCodeDelitionUseCase;
import com.example.mfaservice.application.ports.ports.in.VerificationCodeValidationUseCase;
import com.example.mfaservice.application.ports.ports.out.VerificationCodePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationCodeDeletionService implements VerificationCodeDelitionUseCase {

    private final VerificationCodePort verificationCodePort;

    /**
     * Deletes expired Verification codes from database older than a given timestamp
     * @param timestamp
     * @return
     */
    @Override
    public int deleteExpiredVerificationCodes(LocalDateTime timestamp) {
        return verificationCodePort.deleteExpiredVerificationCodes(timestamp);
    }
}