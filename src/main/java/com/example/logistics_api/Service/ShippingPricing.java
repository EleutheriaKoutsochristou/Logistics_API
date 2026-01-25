package com.example.logistics_api.Service;

import com.example.logistics_api.Enumeration.ShippingType;

public interface ShippingPricing {
    double calculateCost(double weight);
}
