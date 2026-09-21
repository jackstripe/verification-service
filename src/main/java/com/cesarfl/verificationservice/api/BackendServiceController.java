package com.cesarfl.verificationservice.api;

import com.cesarfl.verificationservice.api.dto.VerificationResponse;
import com.cesarfl.verificationservice.application.VerificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BackendServiceController {

    private final VerificationService verificationService;

    public BackendServiceController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/backend-service")
    public VerificationResponse verify(
            @RequestParam UUID verificationId,
            @RequestParam String query) {
        return verificationService.verify(verificationId, query);
    }
}
