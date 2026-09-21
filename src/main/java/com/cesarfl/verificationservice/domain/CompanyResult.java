package com.cesarfl.verificationservice.domain;

import java.time.LocalDate;

public record CompanyResult(String cin,
                            String name,
                            LocalDate registrationDate,
                            String address) {
}
