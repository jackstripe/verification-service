package com.cesarfl.verificationservice.api;

import com.cesarfl.verificationservice.application.FreeCompanySearch;
import com.cesarfl.verificationservice.integration.free.FreeCompany;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FreeThirdPartyController {

    private final FreeCompanySearch freeCompanySearch;

    public FreeThirdPartyController(FreeCompanySearch freeCompanySearch) {
        this.freeCompanySearch = freeCompanySearch;
    }

    @GetMapping("/free-third-party")
    public List<FreeCompany> search(@RequestParam String query) {
        return freeCompanySearch.query(query);
    }
}
