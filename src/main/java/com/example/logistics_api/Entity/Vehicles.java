package com.example.logistics_api.Entity;
import com.example.logistics_api.Enumeration.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name= "vehicles" )
public class Vehicles {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private String licensePlate;

    private Double capacity;
    private Double maxspeed;

    public Vehicles(VehicleType type, String licensePlate, Double capacity, Double maxspeed) {
        this.type = type;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.maxspeed = maxspeed;

    }

    public Vehicles() {}
}
