package com.cesarfl.verificationservice.api;

import com.cesarfl.verificationservice.application.FreeCompanySearch;
import com.cesarfl.verificationservice.integration.free.FreeCompany;
import com.cesarfl.verificationservice.integration.free.FreeProviderAvailability;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class FreeThirdPartyController {

    private final FreeCompanySearch freeCompanySearch;
    private final FreeProviderAvailability freeProviderAvailability;

    public FreeThirdPartyController(FreeCompanySearch freeCompanySearch, FreeProviderAvailability freeProviderAvailability) {
        this.freeCompanySearch = freeCompanySearch;
        this.freeProviderAvailability = freeProviderAvailability;
    }

    @GetMapping("/free-third-party")
    public List<FreeCompany> search(@RequestParam String query) {
        if(freeProviderAvailability.shouldFail()){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service is Not Available");
        }
        return freeCompanySearch.query(query);
    }
}
