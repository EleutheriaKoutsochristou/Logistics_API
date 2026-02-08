package com.example.logistics_api.FactoryPattern;
import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.VehicleType;

import java.util.Optional;

public abstract class VehicleFactory {
    protected abstract Vehicle createVehicleInstance();

    public abstract VehicleType getType();

    public Vehicle prepareVehicle(){
        return createVehicleInstance();
    }
}
