package com.example.logistics_api.Service;

import com.example.logistics_api.DTO.OrderResponseDTO;
import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    private final OrderRepository orderRepository;

    public AnalyticsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //Total revenue from all sales
    public double totalRevenue(){
        return orderRepository.findAll().stream()
                .mapToDouble(Order::getCost)
                .reduce(0, Double::sum);
    }
    public Map<String, List<Order>> getOrders(){
        return orderRepository.findAll().stream()
                .collect(Collectors.groupingBy(Order::getDestination));
    }



    public Optional<Object> mostExpensiveOrder(){
        return orderRepository.findAll().stream()
                .max(Comparator.comparingDouble(Order::getCost)).
                map(OrderResponseDTO::fromEntity);
    }

    public List<Integer> getDelayedOrders(){
        return orderRepository.findAll().stream()
        .filter(c->c.getStatus()== OrderStatus.DELAYED)
        .map(Order::getId)
                .collect(Collectors.toList());
    }
}
