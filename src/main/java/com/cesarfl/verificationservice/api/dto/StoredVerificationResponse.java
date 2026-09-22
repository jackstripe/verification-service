package com.cesarfl.verificationservice.api.dto;

import com.cesarfl.verificationservice.domain.VerificationSource;

import java.time.Instant;
import java.util.UUID;

public record StoredVerificationResponse(
        UUID verificationId,
        String queryText,
        Instant timestamp,
        VerificationResponse result,
        VerificationSource source
) {}