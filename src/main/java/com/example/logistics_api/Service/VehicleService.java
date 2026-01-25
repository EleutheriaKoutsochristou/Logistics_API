package com.example.logistics_api.Service;

import com.example.logistics_api.DTO.VehicleDTO;
import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.VehicleType;
import com.example.logistics_api.Factory.VehicleFactory;
import com.example.logistics_api.Repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.logistics_api.DTO.VehicleDTO.VehicletoDTO;
import static java.util.stream.Collectors.toList;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleFactory vehicleFactory;


    public VehicleService(VehicleRepository vehicleRepository, VehicleFactory vehicleFactory) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
    }

    public VehicleDTO createNewVehicle(String typeStr, String licensePlate) {

        VehicleType type = VehicleType.valueOf(typeStr.toUpperCase()); //type validation
        Vehicle newVehicle = vehicleFactory.createVehicle(type, licensePlate); // create the object based on the licenseplate and the type
        Vehicle savedVehicle = vehicleRepository.save(newVehicle); // save to the database
        return VehicletoDTO(savedVehicle); // return to the database
    }
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream().map(VehicleDTO::VehicletoDTO).toList();
    }



}