package com.example.logistics_api.Controller;
import com.example.logistics_api.DTO.OrderDTO;
import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Repository.OrderRepository;
import com.example.logistics_api.Service.AnalyticsService;
import com.example.logistics_api.Service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping
    public Map<String,Object> analytics(){
        Map<String,Object> result = new HashMap<>();
        result.put("totalRevenue" ,analyticsService.totalRevenue());
        result.put("mostExpensiveOrder",analyticsService.mostExpensiveOrder());
        result.put("delayedOrders",analyticsService.getDelayedOrders());
        result.put("getOrders",analyticsService.getOrders());
        return result;
    }
}

















