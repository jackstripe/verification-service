package com.cesarfl.verificationservice.api;

import com.cesarfl.verificationservice.api.dto.VerificationResponse;
import com.cesarfl.verificationservice.api.dto.VerificationResult;
import com.cesarfl.verificationservice.application.VerificationService;
import com.cesarfl.verificationservice.domain.CompanyResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BackendServiceController.class)
public class BackendServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VerificationService verificationService;


    @Test
    void freeResponseActiveAndNotActive() throws Exception {

        CompanyResult companyResult = new CompanyResult(
                "CIN",
                "Company name",
                LocalDate.of(2026, 9, 21),
                "Company address");

        UUID uuid = UUID.randomUUID();
        VerificationResponse verificationResponse = new VerificationResponse(
                uuid,
                "CIN",
                new VerificationResult("FOUND", companyResult),
                List.of());


        when(verificationService.verify(uuid, "CIN"))
                .thenReturn(verificationResponse);

        mockMvc.perform(get("/backend-service")
                        .param("verificationId", uuid.toString())
                        .param("query", "CIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verificationId").value(uuid.toString()))
                .andExpect(jsonPath("$.query").value("CIN"))
                .andExpect(jsonPath("$.result.status").value("FOUND"))
                .andExpect(jsonPath("$.result.company.cin").value("CIN"))
                .andExpect(jsonPath("$.result.company.name").value("Company name"))
                .andExpect(jsonPath("$.result.company.registrationDate")
                        .value("2026-09-21"))
                .andExpect(jsonPath("$.result.company.address")
                        .value("Company address"))
                .andExpect(jsonPath("$.otherResults").isEmpty());

    }
}
