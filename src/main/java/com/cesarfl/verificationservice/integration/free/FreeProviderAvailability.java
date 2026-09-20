package com.cesarfl.verificationservice.integration.free;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class FreeProviderAvailability {

    public boolean shouldFail(){

        Random random = new Random();
        return random.nextInt(100) < 40;


    }
}
