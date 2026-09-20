package com.cesarfl.verificationservice.integration.premium;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PremiumProviderAvailability {
    public boolean shouldFail(){

        Random random = new Random();
        return random.nextInt(100) < 10;


    }
}
