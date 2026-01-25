package com.example.logistics_api.Entity;

import com.example.logistics_api.DTO.VehicleDTO;
import com.example.logistics_api.Enumeration.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Builder
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false, unique = true)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="type",nullable = false)
    private VehicleType type;

    @Column(name="licenseplate",nullable = false, unique = true)
    private String licensePlate;

    @Column(name="capacity",nullable = false)
    private Double capacity;

    @Column(name="maxspeed",nullable = false)
    private Double maxSpeed;

    @Column(name="status",nullable = false)
    private String status;

    public Vehicle(VehicleType type, String licensePlate, Double capacity, Double maxSpeed) {
        this.type = type;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.maxSpeed = maxSpeed;
        this.status = "TRUE";

    }

    public Vehicle() {

    }

    public Vehicle DtoToVehicle(VehicleDTO vehicleDTO) {
        return Vehicle.builder()
                .type(getType())
                .id(getId())
                .licensePlate(getLicensePlate())
                .capacity(getCapacity())
                .maxSpeed(getMaxSpeed())
                .status(getStatus())
                .build();
    }
}
