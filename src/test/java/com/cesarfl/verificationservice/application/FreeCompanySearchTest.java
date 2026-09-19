package com.cesarfl.verificationservice.application;


import com.cesarfl.verificationservice.integration.free.FreeCompany;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class FreeCompanySearchTest {

    @Autowired
    FreeCompanySearch freeCompanySearch;

    @Test
    void shouldReturnFilteredClasses(){
        FreeCompany freeCompany = new FreeCompany(
                "CJQUNXGW",
                "Ramirez-Sanchez",
                LocalDate.of(2023,6,9),
                "416 Mcdonald Gardens Suite 018, Garciashire, ME 95742",
                true);

        assertThat(freeCompanySearch.query("CJQ"))
                .isEqualTo(List.of(freeCompany));

        assertThat(freeCompanySearch.query(""))
                .isEmpty();

        assertThat(freeCompanySearch.query("DOESNOTEXISTS"))
                .isEmpty();

        assertThat(freeCompanySearch.query("cjq"))
                .containsExactly(freeCompany);

        assertThat(freeCompanySearch.query(" CjQ "))
                .containsExactly(freeCompany);

    }
}
