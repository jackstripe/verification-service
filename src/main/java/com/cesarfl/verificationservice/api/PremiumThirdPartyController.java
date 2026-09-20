package com.cesarfl.verificationservice.api;

import com.cesarfl.verificationservice.application.PremiumCompanySearch;
import com.cesarfl.verificationservice.integration.premium.PremiumCompany;
import com.cesarfl.verificationservice.integration.premium.PremiumProviderAvailability;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PremiumThirdPartyController {

    private final PremiumCompanySearch premiumCompanySearch;
    private final PremiumProviderAvailability premiumProviderAvailability;

    public PremiumThirdPartyController(PremiumCompanySearch premiumCompanySearch, PremiumProviderAvailability premiumProviderAvailability) {
        this.premiumCompanySearch = premiumCompanySearch;
        this.premiumProviderAvailability = premiumProviderAvailability;
    }

    @GetMapping("/premium-third-party")
    public List<PremiumCompany> search(@RequestParam String query) {
        if(premiumProviderAvailability.shouldFail()){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service is Not Available");
        }
        return premiumCompanySearch.query(query);
    }
}
