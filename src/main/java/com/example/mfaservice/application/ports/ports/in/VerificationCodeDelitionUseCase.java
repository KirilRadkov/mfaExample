package com.example.mfaservice.application.ports.ports.in;

import com.example.mfaservice.adapter.out.db.VerificationCodeEntity;

import java.time.LocalDateTime;

public interface VerificationCodeDelitionUseCase {

    int deleteExpiredVerificationCodes(LocalDateTime timestamp);

}
