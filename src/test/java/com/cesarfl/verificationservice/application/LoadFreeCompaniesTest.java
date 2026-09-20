package com.cesarfl.verificationservice.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class LoadFreeCompaniesTest {

    LoadFreeCompanies loadFreeCompanies;

    @Test
    void shouldLoadFreeCompanies(){

        assertThat(loadFreeCompanies.getData()).hasSize(33);
        assertThat(loadFreeCompanies.getData().getFirst().registrationDate())
                .isEqualTo(LocalDate.of(2023, 6, 9));

    }
}
