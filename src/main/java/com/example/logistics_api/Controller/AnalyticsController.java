package com.example.logistics_api.Controller;

import com.example.logistics_api.Service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping
    public Map<String, Object> analytics() {
        Map<String, Object> result = new HashMap<>();
        result.put("totalRevenue", analyticsService.totalRevenue());
        result.put("mostExpensiveOrder", analyticsService.mostExpensiveOrder());
        result.put("delayedOrders", analyticsService.getDelayedOrders());
        result.put("getOrders", analyticsService.getOrders());
        return result;
    }
}













