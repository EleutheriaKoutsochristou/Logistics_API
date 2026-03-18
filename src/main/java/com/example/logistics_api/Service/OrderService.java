package com.example.logistics_api.Service;

import com.example.logistics_api.DTO.OrderDTO;
import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShippingCostService shippingCostService;

    public OrderService(OrderRepository orderRepository, ShippingCostService shippingCostService) {
        this.orderRepository = orderRepository;
        this.shippingCostService = shippingCostService;
    }

    @Transactional
    public int createOrder(OrderDTO dto) {
        Order order = Order.DtoToEntity(dto);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        return order.getId();
    }

    @Async
    @Transactional
    public void executeOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        try {
            Thread.sleep(3000);
            double cost = shippingCostService.calculate(order.getShippingType(), order.getWeight());
            order.setCost(cost);
        } catch (Exception e) {
            order.setStatus(OrderStatus.FAILED);
            e.printStackTrace();
        }
    }
}
