package com.cesarfl.verificationservice.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class LoadCompaniesTest {

    @Autowired
    LoadFreeCompanies loadFreeCompanies;

    @Test
    public void shouldLoadFreeCompanies(){

        assertThat(loadFreeCompanies.getData()).hasSize(33);
        assertThat(loadFreeCompanies.getData().get(0).registrationDate())
                .isEqualTo(LocalDate.of(2023, 6, 9));

    }
}
