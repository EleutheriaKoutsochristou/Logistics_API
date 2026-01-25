package com.example.logistics_api.Service;

import com.example.logistics_api.DTO.OrderDTO;
import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Repository.OrderRepository;
import com.example.logistics_api.Repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShippingCostService shippingCostService;
    private final VehicleRepository vehicleRepository;

    public OrderService(OrderRepository orderRepository,
                        ShippingCostService shippingCostService,
                        VehicleRepository vehicleRepository) {
        this.orderRepository = orderRepository;
        this.vehicleRepository = vehicleRepository;
        this.shippingCostService = shippingCostService;
    }

    @Transactional
    public int createOrder(OrderDTO dto) {
        Order new_order = Order.DtoToEntity(dto);
        new_order.setStatus(OrderStatus.PENDING);
        orderRepository.save(new_order);
        return new_order.getId();

    }

    @Async
    @Transactional
    public void ExecuteOrder(int orderId) {

        try {
            Thread.sleep(3000);
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));


            double cost = shippingCostService.calculate(order.getShippingType(), order.getWeight());
            order.setCost(cost);
            order.setStatus(OrderStatus.READY);
            System.out.println("FOUND THE ORDER!!!");


        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
