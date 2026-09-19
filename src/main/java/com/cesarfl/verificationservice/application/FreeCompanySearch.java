package com.cesarfl.verificationservice.application;

import com.cesarfl.verificationservice.integration.free.FreeCompany;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class FreeCompanySearch {


    private final LoadFreeCompanies loadFreeCompanies;

    public FreeCompanySearch(LoadFreeCompanies loadFreeCompanies){
        this.loadFreeCompanies = loadFreeCompanies;
    }

    public List<FreeCompany> query(String query){
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String normalizedQuery = query.strip().toUpperCase(Locale.ROOT);

        return this.loadFreeCompanies.getData()
                .stream()
                .filter(freeCompany -> freeCompany.cin()
                        .toUpperCase(Locale.ROOT)
                        .contains(normalizedQuery))
                .toList();

    }
}
