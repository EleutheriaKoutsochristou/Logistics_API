package com.example.logistics_api.Service;

import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.logistics_api.DTO.OrderDTO.OrdertoDTO;
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
                .sum();
    }
    public Map<String, List<Order>> getOrders(){
        return orderRepository.findAll().stream()
                .collect(Collectors.groupingBy(Order::getDestination));
    }


    //Most expensive order
    public Record mostExpensiveOrder(){
        return OrdertoDTO(Objects.requireNonNull(orderRepository.findAll().stream()
                .max(Comparator.comparingDouble(Order::getCost))
                .orElse(null)));
    }

    public List<Integer> getDelayedOrders(){
        return orderRepository.findAll().stream()
        .filter(c->c.getStatus()== OrderStatus.DELAYED)
        .map(Order::getId)
                .collect(Collectors.toList());
    }
}
