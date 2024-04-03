package com.example.mfaservice.adapter.out.db;

import com.example.mfaservice.application.ports.ports.out.VerificationCodePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VerificationCodeDbAdapter implements VerificationCodePort {

    private final VerificationCodeRepository verificationCodeRepository;


    @Override
    public Optional<VerificationCodeEntity> findByEmail(String email) {
        return verificationCodeRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void saveVerificationCodeEntity(VerificationCodeEntity entity) {
        verificationCodeRepository.save(entity);
    }

    @Override
    @Transactional
    public int deleteExpiredVerificationCodes(LocalDateTime timestamp) {
        return verificationCodeRepository.deleteVerificationCodeEntitiesByExpirationTimestampBefore(timestamp);
    }

}
