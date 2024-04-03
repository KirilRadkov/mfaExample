package com.example.mfaservice.application.ports.ports.in;

import com.example.mfaservice.adapter.out.db.VerificationCodeEntity;
import com.example.mfaservice.domain.VerificationCodeResult;

public interface VerificationCodeValidationUseCase {

    VerificationCodeResult validateVerificationCode(String email, String verificationCode);

}
