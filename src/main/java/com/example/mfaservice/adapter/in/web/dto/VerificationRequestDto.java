package com.example.mfaservice.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationRequestDto {

    // email should be a valid email format
    // email should not be null or empty
    @Email(message = "Email should be valid")
    @NotEmpty
    private String email;

    private String verificationCode;


}
