package com.example.logistics_api.Service;

import com.example.logistics_api.Enumeration.ShippingType;
import com.example.logistics_api.PricingService.ShippingPricing;
import org.springframework.stereotype.Service;

import java.util.Map;

//Picks the right strategy and calculates cost
@Service
public class ShippingCostService {
    //Map the different types("Standard","free","express" with their respective Pricing classes
    //They all implement ShippingPricing
    private final Map<String, ShippingPricing> pricing_strategies;

    //Spring Maps the beans type ShippingPricing(key=bean name("free"))
    public ShippingCostService(Map<String, ShippingPricing> pricing_strategies) {
        this.pricing_strategies = pricing_strategies;
    }


    public double calculate(ShippingType shippingType, double weight) {
        //Find the right strategy
        ShippingPricing strategy = pricing_strategies.get(shippingType.name());
        if (strategy == null) {
            throw new IllegalArgumentException("Type doesnt exist" + shippingType);
        } else if (weight >= 5.0 && (shippingType == ShippingType.FREE)) {
            throw new IllegalArgumentException("Weight exceeds the limit for free shipping. Maximum weight for FREE shipping is 5.0 kg, but received: " + weight + " kg");
        }
        return strategy.calculateCost(weight);
    }
}
