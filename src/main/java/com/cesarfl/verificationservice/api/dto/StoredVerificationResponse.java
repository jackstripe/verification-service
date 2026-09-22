package com.cesarfl.verificationservice.api.dto;

import com.cesarfl.verificationservice.domain.CompanyResult;
import com.cesarfl.verificationservice.domain.VerificationSource;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record StoredVerificationResponse(
        UUID verificationId,
        String queryText,
        Instant timestamp,
        VerificationResult result,
        List<CompanyResult> otherResults,
        VerificationSource source
) {}