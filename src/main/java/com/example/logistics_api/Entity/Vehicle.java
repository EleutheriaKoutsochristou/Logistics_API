package com.example.logistics_api.Entity;

import com.example.logistics_api.Enumeration.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VehicleType type;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "capacity", nullable = false)
    private Double capacity;

    @Column(name = "max_speed", nullable = false)
    private Double maxSpeed;

    @Column(name = "status", nullable = false)
    private String status;

    public Vehicle(VehicleType type, String licensePlate, Double capacity, Double maxSpeed) {
        this.type = type;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.maxSpeed = maxSpeed;
        this.status = "TRUE";
    }

}
