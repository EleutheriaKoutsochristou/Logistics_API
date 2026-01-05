package com.example.logistics_api.Service;

import com.example.logistics_api.Entity.Vehicles;
import com.example.logistics_api.Enumeration.VehicleType;
import com.example.logistics_api.Factory.VehicleFactory;
import com.example.logistics_api.Repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleFactory vehicleFactory;

    // Σύνδεση με Factory και Repository
    public VehicleService(VehicleRepository vehicleRepository, VehicleFactory vehicleFactory) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
    }

    public Vehicle createNewVehicle(String typeStr, String licensePlate) {

        VehicleType type = VehicleType.valueOf(typeStr.toUpperCase());


        Vehicle newVehicle = vehicleFactory.createVehicle(type, licensePlate);

        return vehicleRepository.save(newVehicle);
    }
}