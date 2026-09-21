package com.cesarfl.verificationservice.api.dto;

import com.cesarfl.verificationservice.domain.CompanyResult;

public record VerificationResult(
        String status,
        CompanyResult company
) {}