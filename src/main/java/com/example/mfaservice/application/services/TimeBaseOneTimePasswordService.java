package com.example.mfaservice.application.services;

import com.example.mfaservice.domain.VerificationCodeResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TimeBaseOneTimePasswordService {

    /**
     * Generates a Verification Code from a given Email address and current timestamp.
     * @param email
     * @return alphanumeric 6-digit code
     */
    public String generateVerificationCode(String email) {
        var timeNow = LocalDateTime.now();
        String str = email + timeNow;
        String uuid = getUuidString(str);
        log.info(getSubstring(uuid));
        return getSubstring(uuid);
    }

    public String getUuidString(String str) {
        return  UUID.nameUUIDFromBytes(str.getBytes()).toString();
    }

    private String getSubstring(String uuid) {
        return uuid.substring(0, 6).toUpperCase();
    }

    /**
     * Validates a given verification code and checks if the code is expired
     * @param verificationCode
     * @param savedVerificationCode
     * @param expirationTimestamp
     * @return {@link com.example.mfaservice.domain.VerificationCode}
     */
    public VerificationCodeResult validateVerificationCode(String verificationCode, String savedVerificationCode, LocalDateTime expirationTimestamp) {
        if (verificationCode!=null && savedVerificationCode!= null){
            var valid = verificationCode.equalsIgnoreCase(savedVerificationCode) && expirationTimestamp.isAfter(LocalDateTime.now());
            return valid ? VerificationCodeResult.SUCCESS : VerificationCodeResult.FAILURE;
        } else {
            return VerificationCodeResult.ERROR;
        }
    }
}
