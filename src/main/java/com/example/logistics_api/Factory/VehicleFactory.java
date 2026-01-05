package com.example.logistics_api.Factory;

import com.example.logistics_api.Entity.Vehicles;
import com.example.logistics_api.Enumeration.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class VehicleFactory {
    public Vehicles createVehicle(VehicleType type, String licensePlate) {

        switch (type) {
            case TRUCK:
                return new Vehicles(type, licensePlate, 15000.0, 90.0);
            case VAN:
                return new Vehicles(type, licensePlate, 3500.0, 120.0);
            case DRONE:
                return new Vehicles(type, licensePlate, 20.0, 60.0);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}