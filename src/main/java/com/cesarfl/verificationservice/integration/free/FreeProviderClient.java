package com.cesarfl.verificationservice.integration.free;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
public class FreeProviderClient {

    private final RestClient restClient;

    private final String freePath;

    public FreeProviderClient(
            RestClient.Builder builder,
            @Value("${freecompany.base-url}") String baseUrl,
            @Value("${freecompany.searchurl}") String freePath) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();

        this.freePath = freePath;
    }

    public List<FreeCompany> search(String query){

        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path(freePath)
                        .queryParam("query",query)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<FreeCompany>>() {});
    }
}
