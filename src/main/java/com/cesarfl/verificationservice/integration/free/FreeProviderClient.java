package com.cesarfl.verificationservice.integration.free;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
public class FreeProviderClient {

    private final RestClient restClient;
    @Value("${freecompany.searchurl}")
    private  String freeUrl;
    @Value("${freecompany.base-url}")
    String baseUrl;

    @Autowired
    public FreeProviderClient(RestClient.Builder restClientBuilder) {

        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public List<FreeCompany> search(String query){

        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path(freeUrl)
                        .queryParam("query",query)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<FreeCompany>>() {});
    }
}
