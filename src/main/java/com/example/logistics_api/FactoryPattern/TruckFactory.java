package com.example.logistics_api.FactoryPattern;

import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.VehicleType;
import com.example.logistics_api.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

//import com.example.logistics_api.Entity.Vehicle;
//import com.example.logistics_api.Enumeration.VehicleType;
//import com.example.logistics_api.Repository.VehicleRepository;
//
//public class TruckFactory extends VehicleFactory{
//    private final VehicleRepository repository;
//
//    public TruckFactory(VehicleRepository repository){
//        this.repository=repository;
//    }
//
//    @Override
//    protected Vehicle buildVehicle() {
//        return repository.findFirstByType(VehicleType.TRUCK);
//    }
//}
@Component
public class TruckFactory extends VehicleFactory {

    @Autowired
    private VehicleRepository repository;

    @Override
    protected Vehicle createVehicleInstance() {
        return repository.findFirstByType(VehicleType.TRUCK);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.TRUCK;
    }
}