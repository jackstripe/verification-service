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
    private String baseUrl;

    public PremiumProviderClient(
                                    RestClient restClient,
                                    @Value("${premiumcompany.searchurl}") String premiumPath,
                                    @Value("${freecompany.base-url}")  String baseUrl) {
        this.restClient = restClient;
        this.baseUrl = baseUrl;
        this.premiumPath = premiumPath;
    }
    @Autowired
    public PremiumProviderClient(RestClient.Builder restClientBuilder) {

        this.restClient = restClientBuilder
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
