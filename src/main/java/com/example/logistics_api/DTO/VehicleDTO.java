package com.example.logistics_api.DTO;

import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.VehicleType;
import lombok.Builder;

@Builder
public record VehicleDTO(
        Integer id,
        VehicleType type,
        String licensePlate,
        Double capacity,
        Double maxSpeed
) {
    public static VehicleDTO VehicletoDTO(Vehicle vehicle) {
        return VehicleDTO.builder().
                id(vehicle.getId()).
                type(vehicle.getType())
                .capacity(vehicle.getCapacity())
                .licensePlate(vehicle.getLicensePlate())
                .maxSpeed(vehicle.getMaxSpeed()).build();

    }
    public VehicleDTO GetTypeVehicleDTO() {
        return VehicleDTO.builder().type(type).build();

    }
}