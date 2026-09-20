package com.cesarfl.verificationservice.api;

import com.cesarfl.verificationservice.application.PremiumCompanySearch;
import com.cesarfl.verificationservice.integration.premium.PremiumCompany;
import com.cesarfl.verificationservice.integration.premium.PremiumProviderAvailability;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PremiumThirdPartyController.class)
 class PremiumThirdPartyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PremiumCompanySearch premiumCompanySearch;

    @MockitoBean
    private PremiumProviderAvailability premiumProviderAvailability;

    @Test
    void providerDown() throws Exception {
        when(premiumProviderAvailability.shouldFail()).thenReturn(true);

        mockMvc.perform(get("/premium-third-party?query=F8O"))
                .andExpect(status().isServiceUnavailable());

        verifyNoInteractions(premiumCompanySearch);
    }

    @Test
    void providerUp() throws Exception {

        PremiumCompany premiumCompany = new PremiumCompany(
                "F8OY0O0W",
                "Young, Gomez and Thompson",
                LocalDate.of(2020,9,21),
                "5003 Ponce Vista, Port Jenniferborough, MT 45805",
                false);

        when(premiumCompanySearch.query("F8O")).thenReturn(List.of(premiumCompany));
        when(premiumProviderAvailability.shouldFail()).thenReturn(false);

        mockMvc.perform(get("/premium-third-party").param("query", "F8O"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyIdentificationNumber").value("F8OY0O0W"))
                .andExpect(jsonPath("$[0].registrationDate").value("2020-09-21"))
                .andExpect(jsonPath("$[0].companyFullAddress")
                        .value("5003 Ponce Vista, Port Jenniferborough, MT 45805"))
                .andExpect(jsonPath("$[0].isActive").value(false));

    }
}
