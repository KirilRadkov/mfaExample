package com.example.mfaservice.utility.crypting;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "crypting")
@RequiredArgsConstructor
@Data
@Slf4j
class CryptingProperties {

    private String cipherSecret;
    private String cipherSalt;

}
