package com.example.logistics_api.Entity;

import com.example.logistics_api.DTO.OrderDTO;
import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Enumeration.ShippingType;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "orders")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 128)
    private String customerName;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false, length = 128)
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ShippingType shippingType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(nullable = false)
    private double cost;

    protected Order(String customerName, double weight, String destination,
                    ShippingType shippingType, double cost) {
        this.customerName = customerName;
        this.weight = weight;
        this.destination = destination;
        this.shippingType = shippingType;
        this.cost = cost;
        this.status = OrderStatus.PENDING;
    }


    public static Order DtoToEntity(OrderDTO order) {
        return Order.builder()
            .customerName(order.customerName())
            .weight(order.weight())
            .destination(order.destination())
            .shippingType(order.shippingType())
            .build();
    }
}