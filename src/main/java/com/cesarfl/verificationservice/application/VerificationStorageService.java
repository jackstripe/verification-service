package com.cesarfl.verificationservice.application;

import com.cesarfl.verificationservice.api.dto.StoredVerificationResponse;
import com.cesarfl.verificationservice.api.dto.VerificationResponse;
import com.cesarfl.verificationservice.domain.VerificationSource;
import com.cesarfl.verificationservice.integration.persistence.VerificationEntity;
import com.cesarfl.verificationservice.integration.persistence.VerificationRepository;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationStorageService {

    private final VerificationRepository verificationRepository;
    private final ObjectMapper objectMapper;

    public VerificationStorageService(
            VerificationRepository verificationRepository,
            ObjectMapper objectMapper) {
        this.verificationRepository = verificationRepository;
        this.objectMapper = objectMapper;
    }

    public void save(
            VerificationResponse response,
            String queryText,
            VerificationSource source) {

        try {
            String resultJson =
                    objectMapper.writeValueAsString(response);

            VerificationEntity entity = new VerificationEntity(
                    response.verificationId(),
                    queryText,
                    Instant.now(),
                    resultJson,
                    source);

            verificationRepository.save(entity);

        } catch (JacksonException exception) {
            throw new IllegalStateException(
                    "Could not serialize verification result",
                    exception);
        }
    }
    public Optional<StoredVerificationResponse> findById(UUID verificationId) {
        return verificationRepository.findById(verificationId)
                .map(this::toResponse);
    }
    private StoredVerificationResponse toResponse(
            VerificationEntity entity) {

        try {
            VerificationResponse backendResponse =
                    objectMapper.readValue(
                            entity.getResultJson(),
                            VerificationResponse.class);
            return new StoredVerificationResponse(
                    entity.getVerificationId(),
                    entity.getQueryText(),
                    entity.getTimestamp(),
                    backendResponse.result(),
                    backendResponse.otherResults(),
                    entity.getSource());

        } catch (JacksonException exception) {
            throw new IllegalStateException(
                    "Could not deserialize verification result",
                    exception);
        }
    }
}
