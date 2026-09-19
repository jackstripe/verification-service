package com.cesarfl.verificationservice.integration.free;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;


public record FreeCompany(String cin,
                          String name,
                          @JsonProperty("registration_date")  LocalDate registrationDate,
                          String address,
                          @JsonProperty("is_active") boolean isActive) {
}
