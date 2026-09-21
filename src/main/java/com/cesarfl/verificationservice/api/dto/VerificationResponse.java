package com.cesarfl.verificationservice.api.dto;

import com.cesarfl.verificationservice.domain.CompanyResult;

import java.util.List;
import java.util.UUID;

public record VerificationResponse(
        UUID verificationId,
        String query,
        VerificationResult  result,
        List<CompanyResult> otherResults){


}
