package com.cesarfl.verificationservice.integration.premium;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;


public record PremiumCompany(String companyIdentificationNumber,
                             String companyName,
                             LocalDate registrationDate,
                             @JsonAlias("fullAddress")
                             @JsonProperty("companyFullAddress")
                                String companyFullAddress,
                            @JsonProperty("isActive") boolean isActive) {
}
