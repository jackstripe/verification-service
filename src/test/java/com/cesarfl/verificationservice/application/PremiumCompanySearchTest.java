package com.cesarfl.verificationservice.application;

import com.cesarfl.verificationservice.integration.free.FreeCompany;
import com.cesarfl.verificationservice.integration.premium.PremiumCompany;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class PremiumCompanySearchTest {

    @Autowired
    PremiumCompanySearch premiumCompanySearch;

    @Test
    void shouldReturnFilteredClasses(){
        PremiumCompany premiumCompany = new PremiumCompany(
                "F8OY0O0W",
                "Young, Gomez and Thompson",
                LocalDate.of(2020,9,21),
                "5003 Ponce Vista, Port Jenniferborough, MT 45805",
                false);

        assertThat(premiumCompanySearch.query("F8O"))
                .isEqualTo(List.of(premiumCompany));

        assertThat(premiumCompanySearch.query(""))
                .isEmpty();

        assertThat(premiumCompanySearch.query("DOESNOTEXISTS"))
                .isEmpty();

        assertThat(premiumCompanySearch.query("f8Oy"))
                .containsExactly(premiumCompany);

        assertThat(premiumCompanySearch.query(" F8Oy "))
                .containsExactly(premiumCompany);

    }
}
