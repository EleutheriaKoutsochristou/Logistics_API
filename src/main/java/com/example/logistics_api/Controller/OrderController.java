package com.example.logistics_api.Controller;

import com.example.logistics_api.DTO.OrderDTO;
import com.example.logistics_api.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> processOrder(@RequestBody OrderDTO orderdto) {
        int orderId = orderService.createOrder(orderdto);
        orderService.executeOrder(orderId);
        return ResponseEntity.accepted().body(orderId);
    }
}
