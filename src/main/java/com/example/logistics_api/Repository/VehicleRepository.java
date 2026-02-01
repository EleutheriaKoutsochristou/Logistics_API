package com.example.logistics_api.Repository;

import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {


    List<Vehicle> status(String status);
    Optional<Vehicle> findFirstByTypeAndStatus(VehicleType type, String status);

    Vehicle getVehiclesByType(VehicleType type);
}