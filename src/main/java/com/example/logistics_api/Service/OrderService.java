package com.example.logistics_api.Service;

import com.example.logistics_api.Entity.Orders;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Enumeration.ShippingType;
import com.example.logistics_api.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Orders createOrder(String customerName, double weight, String destination, ShippingType shippingType, double cost) {
        Orders new_order = new Orders();
        new_order.setCustomerName(customerName);
        new_order.setWeight(weight);
        new_order.setDestination(destination);
        new_order.setShippingType(shippingType);
        new_order.setStatus(OrderStatus.PENDING);
        new_order.setCost(cost);
        return orderRepository.save(new_order); //mapper !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!
        // DTO
    }
}
