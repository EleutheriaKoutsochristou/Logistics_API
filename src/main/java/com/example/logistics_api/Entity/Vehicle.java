package com.example.logistics_api.Entity;

import com.example.logistics_api.DTO.VehicleDTO;
import com.example.logistics_api.Enumeration.VehicleType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false, unique = true)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="type",nullable = false, unique = true)
    private VehicleType type;

    @Column(name="licenseplate",nullable = false, unique = true)
    private String licensePlate;

    @Column(name="capacity",nullable = false, unique = true)
    private Double capacity;

    @Column(name="maxspeed",nullable = false, unique = true)
    private Double maxSpeed;

    public Vehicle(VehicleType type, String licensePlate, Double capacity, Double maxSpeed) {
        this.type = type;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.maxSpeed = maxSpeed;

    }

    public Vehicle() {

    }

    public VehicleDTO toDto() {
        return VehicleDTO.builder()
                .type(getType())
                .id(getId())
                .licensePlate(getLicensePlate())
                .capacity(getCapacity())
                .maxSpeed(getMaxSpeed())
                .build();
    }
}
