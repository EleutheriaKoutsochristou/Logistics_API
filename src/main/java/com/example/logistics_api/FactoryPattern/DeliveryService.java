package com.example.logistics_api.FactoryPattern;

import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    private final Map<VehicleType, VehicleFactory> factoryMap;

    @Autowired
    public DeliveryService(List<VehicleFactory> factories) {
        this.factoryMap = factories.stream()
            .collect(Collectors.toMap(VehicleFactory::getType, f -> f));
    }

    public Vehicle startDelivery(VehicleType type) {
        VehicleFactory factory = factoryMap.get(type);
        if (factory == null) {
            throw new RuntimeException("Unsupported vehicle type: " + type);
        }
        return factory.prepareVehicle();
    }
}