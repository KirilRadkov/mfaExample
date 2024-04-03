package com.example.mfaservice.utility.crypting;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jakarta.persistence.AttributeConverter;

@Slf4j
@AllArgsConstructor
public class CryptingStringConverter implements AttributeConverter<String, String> {

    private CryptingProperties cryptingProperties;
    private static final String UTF_8 = "UTF-8";
    private static final String AES = "AES";

    @Override
    public String convertToDatabaseColumn(final String decryptedCode) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(cryptingProperties.getCipherSalt().getBytes(UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(cryptingProperties.getCipherSecret().getBytes(UTF_8), AES);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(decryptedCode.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            log.error("Exception by crypting", e);
        }

        return null;
    }

    @Override
    public String convertToEntityAttribute(final String encryptedCode) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(cryptingProperties.getCipherSalt().getBytes(UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(cryptingProperties.getCipherSecret().getBytes(UTF_8), AES);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encryptedCode));

            return new String(original);
        } catch (Exception e) {
            log.error("Exception bei decrypting", e);
        }

        return null;
    }
}