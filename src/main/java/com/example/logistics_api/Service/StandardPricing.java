package com.example.logistics_api.Service;

import com.example.logistics_api.Enumeration.ShippingType;
import org.springframework.stereotype.Component;

@Component("STANDARD")
public class StandardPricing implements ShippingPricing {
    @Override
    public double calculateCost(double weight){
        double cost = weight * 2.5;
        return cost * 1.5;
    }
}