package com.example.logistics_api.Service;

import com.example.logistics_api.DTO.VehicleDTO;
import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Entity.Vehicle;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Enumeration.VehicleType;
import com.example.logistics_api.FactoryPattern.DeliveryService;
import com.example.logistics_api.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import static com.example.logistics_api.DTO.VehicleDTO.VehicletoDTO;

@Service
public class VehicleService {
    private final DeliveryService deliveryService;
    private final OrderRepository orderRepository;

    public VehicleService(DeliveryService deliveryService, OrderRepository orderRepository) {
        this.deliveryService = deliveryService;
        this.orderRepository = orderRepository;
    }

    public VehicleDTO createNewVehicle(VehicleType type, int orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        Vehicle newVehicle = deliveryService.startDelivery(type);
        performInventoryCheck(newVehicle, order);
        return VehicletoDTO(newVehicle);
    }

    public void performInventoryCheck(Vehicle vehicle, Order order) {
        if ("in_service".equalsIgnoreCase(vehicle.getStatus())) {
            order.setStatus(OrderStatus.DELAYED);
        } else {
            order.setStatus(OrderStatus.READY);
        }
        orderRepository.save(order);
    }
}