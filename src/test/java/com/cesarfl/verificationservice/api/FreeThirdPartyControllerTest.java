package com.cesarfl.verificationservice.api;


import com.cesarfl.verificationservice.application.FreeCompanySearch;
import com.cesarfl.verificationservice.integration.free.FreeCompany;
import com.cesarfl.verificationservice.integration.free.FreeProviderAvailability;
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

@WebMvcTest(FreeThirdPartyController.class)
class FreeThirdPartyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FreeCompanySearch freeCompanySearch;

    @MockitoBean
    private FreeProviderAvailability freeProviderAvailability;

    @Test
    void providerDown() throws Exception {
        when(freeProviderAvailability.shouldFail()).thenReturn(true);

         mockMvc.perform(get("/free-third-party?query=CJQ"))
                .andExpect(status().isServiceUnavailable());

        verifyNoInteractions(freeCompanySearch);
    }

    @Test
    void providerUp() throws Exception {

        FreeCompany freeCompany = new FreeCompany(
                "CJQUNXGW",
                "Ramirez-Sanchez",
                LocalDate.of(2023,6,9),
                "416 Mcdonald Gardens Suite 018, Garciashire, ME 95742",
                true);

        when(freeCompanySearch.query("CJQ")).thenReturn(List.of(freeCompany));
        when(freeProviderAvailability.shouldFail()).thenReturn(false);

        mockMvc.perform(get("/free-third-party").param("query", "CJQ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cin").value("CJQUNXGW"))
                .andExpect(jsonPath("$[0].registration_date").value("2023-06-09"))
                .andExpect(jsonPath("$[0].is_active").value(true));

    }
}
