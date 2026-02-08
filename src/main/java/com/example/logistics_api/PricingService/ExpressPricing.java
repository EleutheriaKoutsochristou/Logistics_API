package com.example.logistics_api.PricingService;

import org.springframework.stereotype.Component;

@Component("EXPRESS")
public class ExpressPricing implements ShippingPricing {
    @Override
    public double calculateCost(double weight){
        double cost = weight * 2.5;
        return cost * 1.5;
    }
}
