package com.example.mfaservice.domain;

public enum VerificationCodeResult {
    SUCCESS ("The verification code is correct"),
    FAILURE("The verification code is NOT correct"),
    ERROR ("Verification error");

    public final String message;


    VerificationCodeResult(String message) {
        this.message = message;
    }

}
