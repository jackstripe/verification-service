package com.cesarfl.verificationservice.integration.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VerificationRepository  extends JpaRepository<VerificationEntity, UUID> {
}
