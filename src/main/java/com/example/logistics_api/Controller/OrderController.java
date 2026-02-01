package com.example.logistics_api.Controller;

import com.example.logistics_api.DTO.OrderDTO;
import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Repository.OrderRepository;
import com.example.logistics_api.Service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.logistics_api.DTO.OrderDTO.OrdertoDTO;
import static com.example.logistics_api.Entity.Order.DtoToEntity;


@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> processOrder(@RequestBody OrderDTO orderdto) {
        System.out.println("Accepting order");
        int order_id = orderService.createOrder(orderdto);
        System.out.println("Order saved and returning the id of the order");

        orderService.executeOrder(order_id);
        System.out.println(order_id);
        return ResponseEntity.accepted().body(order_id);
    }
}
