package com.example.logistics_api.Service;

import com.example.logistics_api.Enumeration.ShippingType;
import org.springframework.stereotype.Component;

@Component("EXPRESS")
public class ExpressPricing implements ShippingPricing{
    @Override
    public double calculateCost(double weight){
        return weight * 2.5;
    }
}
