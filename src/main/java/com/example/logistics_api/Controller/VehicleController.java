package com.example.logistics_api.Controller;
import com.example.logistics_api.DTO.VehicleRequestDTO;
import com.example.logistics_api.DTO.VehicleDTO;
import com.example.logistics_api.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/bindVehicleToOrder")
    public ResponseEntity<VehicleDTO> bindVehicleToOrder(@RequestBody VehicleRequestDTO request) {
        VehicleDTO vehicle = vehicleService.createNewVehicle(request.getType(), request.getOrderId());
        return ResponseEntity.ok(vehicle);
    }
}
