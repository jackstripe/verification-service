package com.cesarfl.verificationservice.application;

import com.cesarfl.verificationservice.integration.free.FreeCompany;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Service
public class LoadFreeCompanies {

    private final ObjectMapper objectMapper;
    private List<FreeCompany> loadedData;

    @Autowired
    public LoadFreeCompanies(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() throws Exception {
        ClassPathResource resource = new ClassPathResource("free_service_companies-1.json");
        try (InputStream inputStream = resource.getInputStream()) {
            this.loadedData = objectMapper.readValue(inputStream,
                    new TypeReference<List<FreeCompany>>() {});
        }
    }

    public List<FreeCompany> getData() {
        return loadedData;
    }

}
