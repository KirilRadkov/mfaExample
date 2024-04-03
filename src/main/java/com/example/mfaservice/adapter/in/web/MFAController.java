package com.example.mfaservice.adapter.in.web;

import com.example.mfaservice.adapter.in.web.dto.VerificationCodeRequestDto;
import com.example.mfaservice.adapter.in.web.dto.VerificationCodeResponseDto;
import com.example.mfaservice.adapter.in.web.dto.VerificationRequestDto;
import com.example.mfaservice.adapter.in.web.dto.VerificationResponseDto;
import com.example.mfaservice.application.ports.ports.in.VerificationCodeCreationUseCase;
import com.example.mfaservice.application.ports.ports.in.VerificationCodeValidationUseCase;
import com.example.mfaservice.domain.VerificationCodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api/rest/")
@Slf4j
public class MFAController {

    private final VerificationCodeCreationUseCase verificationCodeCreationUseCase;
    private final VerificationCodeValidationUseCase verificationCodeValidationUseCase;

    @PostMapping(value = {"code"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generates unique verification code for the e-mail address from the Request-DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The verification Code was generated successfully"),
            @ApiResponse(responseCode = "500", description = "Interner Server Error")
    })
    @ResponseBody
    public ResponseEntity<?> generateVerificationCode(@Valid @RequestBody VerificationCodeRequestDto verificationCodeRequestDto) {
        log.info("Generate unique verification code for {}", verificationCodeRequestDto.getEmail());
        var code = verificationCodeCreationUseCase.generateAndSendVerificationCodeEmail(verificationCodeRequestDto.getEmail());
        log.info("Verification Code was sent to {}", verificationCodeRequestDto.getEmail());
        return ResponseEntity.ok().body(VerificationCodeResponseDto.builder().code(code).build());

    }

    @PostMapping(value = {"verification"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Verifies a Verification Code for a given E-mail address from the Request-DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The verification Code was verified successfully"),
            @ApiResponse(responseCode = "400", description = "The verification Code ist not valid or expired"),
            @ApiResponse(responseCode = "500", description = "Interner Server Error")
    })
    @ResponseBody
    public ResponseEntity<VerificationResponseDto> verifyCode(@Valid @RequestBody VerificationRequestDto verificationRequestDto) {
        log.info("Try to verification code for {}", verificationRequestDto.getEmail());
        var result = verificationCodeValidationUseCase.validateVerificationCode(verificationRequestDto.getEmail(), verificationRequestDto.getVerificationCode());
        log.info(result.message);
        if (result.equals(VerificationCodeResult.SUCCESS)){
            return ResponseEntity.ok().body(VerificationResponseDto.builder().result(result.message).build());
        } else {
            return ResponseEntity.badRequest().body(VerificationResponseDto.builder().result(result.message).build());
        }

    }

}
