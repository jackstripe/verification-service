package com.cesarfl.verificationservice.application;

import com.cesarfl.verificationservice.integration.premium.PremiumCompany;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class LoadPremiumCompanies {

    private final ObjectMapper objectMapper;
    private List<PremiumCompany> loadedData;

    public LoadPremiumCompanies(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource resource = new ClassPathResource("premium_service_companies-1.json");
        try (InputStream inputStream = resource.getInputStream()) {
            this.loadedData = objectMapper.readValue(inputStream, new TypeReference<>() { });
        }
    }


    public List<PremiumCompany> getData() {
        return loadedData;
    }
}
