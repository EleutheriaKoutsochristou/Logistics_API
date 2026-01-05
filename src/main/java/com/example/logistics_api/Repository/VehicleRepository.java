package com.example.logistics_api.Repository;

import com.example.logistics_api.Entity.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicles, Integer> {


}