package com.cesarfl.verificationservice.application;

import com.cesarfl.verificationservice.Service.VerificationStorageService;
import com.cesarfl.verificationservice.api.dto.VerificationResponse;
import com.cesarfl.verificationservice.domain.CompanyResult;
import com.cesarfl.verificationservice.domain.VerificationSource;
import com.cesarfl.verificationservice.integration.free.FreeCompany;
import com.cesarfl.verificationservice.integration.free.FreeProviderClient;
import com.cesarfl.verificationservice.integration.premium.PremiumCompany;
import com.cesarfl.verificationservice.integration.premium.PremiumProviderClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class VerificationServiceTest {

    @Mock
    private FreeProviderClient freeProviderClient;

    @Mock
    private PremiumProviderClient premiumProviderClient;

    @Mock
    private VerificationStorageService verificationStorageService;

    @InjectMocks
    private VerificationService verificationService;


    @Test
    void shouldReturnFoundAndPremiumIsNotInvoked(){

        UUID uuid = UUID.randomUUID();
        String query = "CIN";

        FreeCompany activeCompany = new FreeCompany(
                "CIN123",
                "Active company",
                LocalDate.of(2026, 9, 21),
                "Active address",
                true);

        FreeCompany inactiveCompany = new FreeCompany(
                "CIN456",
                "Inactive company",
                LocalDate.of(2025, 5, 10),
                "Inactive address",
                false);

        CompanyResult expectedCompany = new CompanyResult(
                "CIN123",
                "Active company",
                LocalDate.of(2026, 9, 21),
                "Active address");

        when(freeProviderClient.search(query))
                .thenReturn(List.of(activeCompany, inactiveCompany));

        VerificationResponse response =
                verificationService.verify(uuid, query);

        assertThat(response.verificationId()).isEqualTo(uuid);
        assertThat(response.query()).isEqualTo(query);
        assertThat(response.result().status()).isEqualTo("FOUND");
        assertThat(response.result().company()).isEqualTo(expectedCompany);
        assertThat(response.otherResults()).isEmpty();

        verify(freeProviderClient).search(query);
        verifyNoInteractions(premiumProviderClient);
        verify(verificationStorageService)
                .save(response, query, VerificationSource.FREE);

    }

    @Test
    void shouldFallBackToPremiumWhenFreeReturnsNoResults(){
        UUID uuid = UUID.randomUUID();
        String query = "F8O";

        PremiumCompany activeCompany = new PremiumCompany(
                "F8O123",
                "Active company",
                LocalDate.of(2020, 6, 19),
                "Active address",
                true);

        PremiumCompany inactiveCompany = new PremiumCompany(
                "F8O456",
                "Inactive company",
                LocalDate.of(2022, 2, 22),
                "Inactive address",
                false);

        CompanyResult expectedCompany = new CompanyResult(
                "F8O123",
                "Active company",
                LocalDate.of(2020, 6, 19),
                "Active address");

        when(freeProviderClient.search(query))
                .thenReturn(List.of());

        when(premiumProviderClient.search(query))
                .thenReturn(List.of(activeCompany, inactiveCompany));

        VerificationResponse response =
                verificationService.verify(uuid, query);

        assertThat(response.verificationId()).isEqualTo(uuid);

        assertThat(response.query()).isEqualTo(query);
        assertThat(response.result().status()).isEqualTo("FOUND");
        assertThat(response.result().company()).isEqualTo(expectedCompany);
        assertThat(response.otherResults()).isEmpty();

        verify(premiumProviderClient).search(query);
        verify(freeProviderClient).search(query);
    }
    @Test
    void shouldFallBackToPremiumWhenFreeReturns503(){
        UUID uuid = UUID.randomUUID();
        String query = "F8O";

        PremiumCompany activeCompany = new PremiumCompany(
                "F8O123",
                "Active company",
                LocalDate.of(2020, 6, 19),
                "Active address",
                true);



        CompanyResult expectedCompany = new CompanyResult(
                "F8O123",
                "Active company",
                LocalDate.of(2020, 6, 19),
                "Active address");

        when(freeProviderClient.search(query))
                .thenThrow(new HttpServerErrorException(
                        HttpStatus.SERVICE_UNAVAILABLE));

        when(premiumProviderClient.search(query))
                .thenReturn(List.of(activeCompany));
        VerificationResponse response =
                verificationService.verify(uuid, query);

        assertThat(response.verificationId()).isEqualTo(uuid);

        assertThat(response.query()).isEqualTo(query);
        assertThat(response.result().status()).isEqualTo("FOUND");
        assertThat(response.result().company()).isEqualTo(expectedCompany);
        assertThat(response.otherResults()).isEmpty();

        verify(freeProviderClient).search(query);
        verify(premiumProviderClient).search(query);

    }

    @Test
    void shouldFreeAndPremiumReturns503IsProvidersUnavailable(){
        UUID uuid = UUID.randomUUID();
        String query = "F8O";


        when(freeProviderClient.search(query))
                .thenThrow(new HttpServerErrorException(
                        HttpStatus.SERVICE_UNAVAILABLE));

        when(premiumProviderClient.search(query))
                .thenThrow(new HttpServerErrorException(
                        HttpStatus.SERVICE_UNAVAILABLE));
        VerificationResponse response =
                verificationService.verify(uuid, query);

        assertThat(response.verificationId()).isEqualTo(uuid);
        assertThat(response.query()).isEqualTo(query);
        assertThat(response.result().status()).isEqualTo("PROVIDERS_UNAVAILABLE");
        assertThat(response.result().company()).isNull();
        assertThat(response.otherResults()).isEmpty();

        verify(freeProviderClient).search(query);
        verify(premiumProviderClient).search(query);

    }
}
