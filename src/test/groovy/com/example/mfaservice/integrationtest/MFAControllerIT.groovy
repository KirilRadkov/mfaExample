package com.example.mfaservice.integrationtest

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class MFAControllerIT extends Specification {

    @Autowired
    private MockMvc mockMvc

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) throws IOException {
        r.add("jasypt.encryptor.password", () -> "mfanexo")
    }

    def "GenerateVerificationCode: Email valid"() {
        given:
        def body = Map.of("email", "email@email.com");

        when:
        def response = generateVerificationCode(body)

        then:
        response.andExpect(status().is2xxSuccessful())
    }

    def "GenerateVerificationCode: Email not valid"() {
        given:
        def body = Map.of("email", "email.com");

        when:
        def response = generateVerificationCode(body)

        then:
        response.andExpect(status().is4xxClientError())
    }

    def "VerifyCode: Email valid, code Valid"() {
        given:
        def requestBody = Map.of("email", "email@email.com");
        def result = generateVerificationCode(requestBody).andReturn()
        def code  = JsonPath.read(result.getResponse().getContentAsString(), "\$.code")
        def body = Map.of("email", "email@email.com", "verificationCode", code);


        when:
        def response = verifyCode(body)

        then:
        response.andExpect(status().is2xxSuccessful())
    }

    def "VerifyCode: Email valid, code not valid"() {
        given:
        def body = Map.of("email", "email@email.com", "verificationCode", "code");

        when:
        def response = verifyCode(body)

        then:
        response.andExpect(status().is4xxClientError())
    }

    def "VerifyCode: Email not valid"() {
        given:
        def body = Map.of("email", "email.com", "verificationCode", "code");

        when:
        def response = verifyCode(body)

        then:
        response.andExpect(status().is4xxClientError())
    }

    private def generateVerificationCode(body) {
        ObjectMapper objectMapper = new ObjectMapper()

        mockMvc.perform(post("/api/rest/code")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
    }

    private def verifyCode(body) {
        ObjectMapper objectMapper = new ObjectMapper()

        mockMvc.perform(post("/api/rest/verification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
    }

}
