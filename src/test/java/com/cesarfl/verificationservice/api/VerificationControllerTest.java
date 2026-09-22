package com.cesarfl.verificationservice.api;

import com.cesarfl.verificationservice.application.VerificationStorageService;
import com.cesarfl.verificationservice.api.dto.StoredVerificationResponse;
import com.cesarfl.verificationservice.api.dto.VerificationResponse;
import com.cesarfl.verificationservice.api.dto.VerificationResult;
import com.cesarfl.verificationservice.domain.CompanyResult;
import com.cesarfl.verificationservice.domain.VerificationSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VerificationController.class)
class VerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VerificationStorageService verificationStorageService;

    @Test
    void shouldReturnStoredVerification() throws Exception {

        UUID uuid = UUID.randomUUID();

        CompanyResult company = new CompanyResult(
                "CIN123",
                "Company name",
                LocalDate.of(2026, 9, 22),
                "Company address");

        VerificationResponse backendResponse =
                new VerificationResponse(
                        uuid,
                        "CIN",
                        new VerificationResult("FOUND", company),
                        List.of());

        StoredVerificationResponse storedVerification =
                new StoredVerificationResponse(
                        uuid,
                        "CIN",
                        Instant.parse("2026-09-22T06:55:18Z"),
                        backendResponse.result(),
                        backendResponse.otherResults(),
                        VerificationSource.FREE);

        when(verificationStorageService.findById(uuid))
                .thenReturn(Optional.of(storedVerification));

        mockMvc.perform(get("/verifications/{verificationId}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verificationId")
                        .value(uuid.toString()))
                .andExpect(jsonPath("$.queryText").value("CIN"))
                .andExpect(jsonPath("$.source").value("FREE"))
                .andExpect(jsonPath("$.result.status")
                        .value("FOUND"))
                .andExpect(jsonPath("$.result.company.cin")
                        .value("CIN123"))
                .andExpect(jsonPath("$.otherResults").isEmpty());
    }

    @Test
    void shouldReturn404WhenVerificationDoesNotExist()
            throws Exception {

        UUID uuid = UUID.randomUUID();

        when(verificationStorageService.findById(uuid))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/verifications/{verificationId}", uuid))
                .andExpect(status().isNotFound());
    }
}