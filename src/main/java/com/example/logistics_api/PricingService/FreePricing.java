package com.example.logistics_api.PricingService;

import org.springframework.stereotype.Component;

@Component("FREE")
public class FreePricing implements ShippingPricing {
    //private static final double max_weight = 5;
    @Override
    public double calculateCost(double weight){
        return 0.0;
    }
}
