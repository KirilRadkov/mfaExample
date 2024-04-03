package com.example.mfaservice.application.services;

import com.example.mfaservice.application.ports.ports.in.VerificationCodeValidationUseCase;
import com.example.mfaservice.application.ports.ports.out.VerificationCodePort;
import com.example.mfaservice.domain.VerificationCodeResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationCodeValidationService implements VerificationCodeValidationUseCase {

    private final TimeBaseOneTimePasswordService timeBaseOneTimePasswordService;
    private final VerificationCodePort verificationCodePort;

    /**
     * Starts the validation of given Verification code
     * @param email
     * @param verificationCode
     * @return {@link com.example.mfaservice.domain.VerificationCode}
     */
    @Override
    public VerificationCodeResult validateVerificationCode(String email, String verificationCode) {
        var savedVerificationCode = verificationCodePort.findByEmail(email);
        if (savedVerificationCode.isPresent()){
            return timeBaseOneTimePasswordService.validateVerificationCode(
                    verificationCode,
                    savedVerificationCode.get().getVerificationCode(),
                    savedVerificationCode.get().getExpirationTimestamp());
        } else {
            return VerificationCodeResult.ERROR;
        }

    }


}