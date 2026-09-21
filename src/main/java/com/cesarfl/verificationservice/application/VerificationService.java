package com.cesarfl.verificationservice.application;

import com.cesarfl.verificationservice.api.dto.VerificationResponse;
import com.cesarfl.verificationservice.api.dto.VerificationResult;
import com.cesarfl.verificationservice.domain.CompanyResult;
import com.cesarfl.verificationservice.integration.free.FreeCompany;
import com.cesarfl.verificationservice.integration.free.FreeProviderClient;
import com.cesarfl.verificationservice.integration.premium.PremiumCompany;
import com.cesarfl.verificationservice.integration.premium.PremiumProviderClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.UUID;

@Component
public class VerificationService {

    private final PremiumProviderClient premiumProviderClient;
    private final FreeProviderClient freeProviderClient;

    public VerificationService(PremiumProviderClient premiumProviderClient, FreeProviderClient freeProviderClient) {
        this.premiumProviderClient = premiumProviderClient;
        this.freeProviderClient = freeProviderClient;
    }

    public VerificationResponse verify(UUID verificationId, String query){

        List<FreeCompany> freeCompanyList;
        List<PremiumCompany> premiumCompanyList;

        try {
            freeCompanyList = freeProviderClient.search(query);
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().value() != 503) {
                throw ex;
            }
            freeCompanyList = List.of();
        }
        if (!freeCompanyList.isEmpty()) {
            return freeResponse(verificationId, query, freeCompanyList);
        }


        try{
            premiumCompanyList = premiumProviderClient.search(query);
        }catch (RestClientResponseException ex){
            if (ex.getStatusCode().value() != 503) {
                throw ex;
            }
            return new VerificationResponse(
                    verificationId,
                    query,
                    new VerificationResult("PROVIDERS_UNAVAILABLE", null),
                    List.of());
        }
        return premiumResponse(verificationId, query, premiumCompanyList);

    }

    private VerificationResponse freeResponse(UUID verificationId, String query, List<FreeCompany> freeCompanyList){

        List<CompanyResult> active = freeCompanyList.stream()
                .filter(FreeCompany::isActive)
                .map(this::toCompanyResult)
                .toList();

        return this.buildResponse(verificationId,query,active);
    }

    private VerificationResponse premiumResponse(UUID verificationId, String query,  List<PremiumCompany> premiumCompanyList){

        List<CompanyResult> active = premiumCompanyList.stream()
                .filter(PremiumCompany::isActive)
                .map(this::toCompanyResult)
                .toList();
        return this.buildResponse(verificationId,query,active);
    }

    private CompanyResult toCompanyResult(FreeCompany company) {
        return new CompanyResult(
                company.cin(),
                company.name(),
                company.registrationDate(),
                company.address());
    }

    private CompanyResult toCompanyResult(PremiumCompany company) {
        return new CompanyResult(
                company.companyIdentificationNumber(),
                company.companyName(),
                company.registrationDate(),
                company.companyFullAddress());
    }
    private VerificationResponse buildResponse(
            UUID verificationId, String query, List<CompanyResult> active) {

        if (active.isEmpty()) {
            return new VerificationResponse(
                    verificationId, query,
                    new VerificationResult("NO_RESULTS", null),
                    List.of());
        }

        return new VerificationResponse(
                verificationId, query,
                new VerificationResult("FOUND", active.getFirst()),
                active.subList(1, active.size()));
    }
}
