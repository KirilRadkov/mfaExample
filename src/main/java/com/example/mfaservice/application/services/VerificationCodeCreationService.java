package com.example.mfaservice.application.services;

import com.example.mfaservice.adapter.out.db.VerificationCodeEntity;
import com.example.mfaservice.application.ports.ports.in.VerificationCodeCreationUseCase;
import com.example.mfaservice.application.ports.ports.out.VerificationCodePort;
import com.example.mfaservice.domain.VerificationCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationCodeCreationService implements VerificationCodeCreationUseCase {

    private final EmailSenderService emailSenderService;
    private final TimeBaseOneTimePasswordService timeBaseOneTimePasswordService;
    private final VerificationCodePort verificationCodePort;

    /**
     * Generates verification code for a given Email and send an Email with the alphanumeric 6-digit code
     * @param email
     * @return alphanumeric 6-digit code
     */
    @Override
    public String generateAndSendVerificationCodeEmail(String email) {
        var verificationCode = generateVerificationCode(email);
        var existingVerificationCode = verificationCodePort.findByEmail(email);

        //Create or update existing entity
        existingVerificationCode.ifPresentOrElse(
                entity ->{
                    entity.setVerificationCode(verificationCode);
                    entity.setExpirationTimestamp(LocalDateTime.now().plusMinutes(5));
                    verificationCodePort.saveVerificationCodeEntity(entity);
                    },
                () ->verificationCodePort.saveVerificationCodeEntity(createVerificationCodeEntity(email, verificationCode))
        );
        sendVerificationCodeEmail(email, verificationCode);
        return verificationCode;
    }

    @Override
    public String generateVerificationCode(String email) {
        return timeBaseOneTimePasswordService.generateVerificationCode(email);
    }

    @Override
    public void sendVerificationCodeEmail(String email, String verificationCode) {
        var emailBody = createEmailBody(verificationCode);
        emailSenderService.sendEmail(email, emailBody);
    }

    /**
     * Generates the body part for the Email
     * @param verificationCode
     * @return created Email body
     */
    @Override
    public String createEmailBody(String verificationCode) {
        return "Your verification code is: " + verificationCode + " This code is valid only 5 minutes!";
    }

    /**
     * Creates a database Entity from given VerificationCode-Domain object
     * @param email
     * @param verificationCode
     * @return entity to be saved
     */
    @Override
    public VerificationCodeEntity createVerificationCodeEntity(String email, String verificationCode) {
        var verificationCodeObject = VerificationCode.builder()
                                        .verificationCode(verificationCode)
                                        .email(email)
                                        .expirationTimestamp(LocalDateTime.now().plusMinutes(5))
                                        .build();
        return verificationCodeObject.mapToEntity();
    }
}