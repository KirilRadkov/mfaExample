package com.example.mfaservice.adapter.out.db;

import com.example.mfaservice.utility.crypting.CryptingStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_code")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCodeEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY )
    private long id;

    @Convert(converter = CryptingStringConverter.class)
    private String verificationCode;

    private String email;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    private LocalDateTime expirationTimestamp;



}
