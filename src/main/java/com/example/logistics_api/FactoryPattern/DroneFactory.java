package com.example.logistics_api.FactoryPattern;

import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.VehicleType;
import com.example.logistics_api.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DroneFactory extends VehicleFactory{
    @Autowired
    private VehicleRepository repository;

    @Override
    protected Vehicle createVehicleInstance() {
        return repository.findFirstByType(VehicleType.DRONE);

    }

    @Override
    public VehicleType getType() {
        return VehicleType.DRONE;
    }
}

