package com.example.logistics_api.PricingService;

import org.springframework.stereotype.Component;

@Component("STANDARD")
public class StandardPricing implements ShippingPricing {
    @Override
    public double calculateCost(double weight){
        return weight * 2.5;
    }
}