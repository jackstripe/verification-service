package com.cesarfl.verificationservice.api;

import com.cesarfl.verificationservice.application.VerificationStorageService;
import com.cesarfl.verificationservice.api.dto.StoredVerificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class VerificationController {

    private final VerificationStorageService verificationStorageService;

    public VerificationController(  VerificationStorageService verificationStorageService) {
        this.verificationStorageService = verificationStorageService;
    }

    @GetMapping("/verifications/{verificationId}")
    public ResponseEntity<StoredVerificationResponse> findById(
            @PathVariable UUID verificationId) {

        return verificationStorageService.findById(verificationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
