package com.cesarfl.verificationservice.application;

import com.cesarfl.verificationservice.integration.free.FreeCompany;
import com.cesarfl.verificationservice.integration.premium.PremiumCompany;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class PremiumCompanySearch {

    private final LoadPremiumCompanies loadPremiumCompanies;

    public PremiumCompanySearch(LoadPremiumCompanies loadPremiumCompanies){
        this.loadPremiumCompanies = loadPremiumCompanies;
    }

    public List<PremiumCompany> query(String query){
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String normalizedQuery = query.strip().toUpperCase(Locale.ROOT);

        return this.loadPremiumCompanies.getData()
                .stream()
                .filter(premiumCompany -> premiumCompany.companyIdentificationNumber()
                        .toUpperCase(Locale.ROOT)
                        .contains(normalizedQuery))
                .toList();

    }
}
