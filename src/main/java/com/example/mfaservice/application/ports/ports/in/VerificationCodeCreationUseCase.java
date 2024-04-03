package com.example.mfaservice.application.ports.ports.in;

import com.example.mfaservice.adapter.out.db.VerificationCodeEntity;

public interface VerificationCodeCreationUseCase {

    String generateAndSendVerificationCodeEmail(String email);

    String generateVerificationCode(String email);

    void sendVerificationCodeEmail(String email, String verificationCode);

    String createEmailBody(String verificationCode);

    VerificationCodeEntity createVerificationCodeEntity(String email, String verificationCode);

}
