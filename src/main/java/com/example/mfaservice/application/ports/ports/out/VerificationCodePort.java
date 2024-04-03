package com.example.mfaservice.application.ports.ports.out;

import com.example.mfaservice.adapter.out.db.VerificationCodeEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationCodePort {

    Optional<VerificationCodeEntity> findByEmail(String email);
    void saveVerificationCodeEntity(VerificationCodeEntity entity);

    int deleteExpiredVerificationCodes(LocalDateTime timestamp);

}
