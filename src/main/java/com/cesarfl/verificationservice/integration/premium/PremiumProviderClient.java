package com.cesarfl.verificationservice.integration.premium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PremiumProviderClient {

    private final RestClient restClient;
    private  String premiumPath;


    public PremiumProviderClient(
            RestClient.Builder builder,
            @Value("${premiumcompany.searchurl}") String premiumPath,
            @Value("${freecompany.base-url}")  String baseUrl) {

        this.premiumPath = premiumPath;
        this.restClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();

    }

    public List<PremiumCompany> search(String query){

        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path(premiumPath)
                        .queryParam("query",query)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<PremiumCompany>>() {});
    }
}
