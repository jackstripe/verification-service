package com.cesarfl.verificationservice.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class LoadPremiumCompaniesTest {

    @Autowired
    LoadPremiumCompanies loadPremiumCompanies;

    @Test
    void shouldLoadPremiumCompanies(){

        assertThat(loadPremiumCompanies.getData()).hasSize(50);
        assertThat(loadPremiumCompanies.getData().getFirst().registrationDate())
                .isEqualTo(LocalDate.of(2020, 9, 21));
        assertThat(loadPremiumCompanies.getData().getFirst().isActive())
                .isFalse();
        assertThat(loadPremiumCompanies.getData().getFirst().companyFullAddress())
                .isNotEmpty();

    }
}
