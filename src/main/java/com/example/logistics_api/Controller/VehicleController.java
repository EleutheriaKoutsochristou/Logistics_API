package com.example.logistics_api.Controller;
import com.example.logistics_api.DTO.VehicleDTO;
import com.example.logistics_api.Repository.OrderRepository;
import com.example.logistics_api.Service.OrderService;
import com.example.logistics_api.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles/init")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<VehicleDTO> initializeVehicles(@RequestBody VehicleDTO vehicleDTO ) {
        VehicleDTO vehicle = vehicleService.createNewVehicle(vehicleDTO.GetTypeVehicleDTO().type());

        return ResponseEntity.ok(vehicle);
//        return ResponseEntity.ok("Initialized " + numberOfVehicles + " vehicles.");

//        return null;
    }



}
