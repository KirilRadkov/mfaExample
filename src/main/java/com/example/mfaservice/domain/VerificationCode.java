package com.example.mfaservice.domain;

import com.example.mfaservice.adapter.out.db.VerificationCodeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class VerificationCode {

    private long id;
    private String verificationCode;
    private String email;
    private LocalDateTime created;
    private LocalDateTime expirationTimestamp;

    public VerificationCodeEntity mapToEntity(){
        return  VerificationCodeEntity.builder()
                .verificationCode(verificationCode)
                .email(email)
                .expirationTimestamp(expirationTimestamp)
                .build();
    }

}
