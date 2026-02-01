package com.example.logistics_api.Factory;

import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.VehicleType;
import com.example.logistics_api.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VehicleFactory {
    @Autowired
    private VehicleRepository repository;
    public Vehicle createVehicle(VehicleType type) {
        switch (type) {
            case TRUCK:
//                () -> repository.getVehiclesByType(type).get(0);

                return repository.getVehiclesByType(type);
            case VAN:
//                return new Vehicle(type, licensePlate, 3500.0, 120.0);
                repository.getVehiclesByType(type);
            case DRONE:
//                return new Vehicle(type, licensePlate, 20.0, 60.0);
                repository.getVehiclesByType(type);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }

    }
}