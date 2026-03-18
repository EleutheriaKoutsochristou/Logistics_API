package com.example.logistics_api.DTO;

import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Enumeration.ShippingType;
import lombok.Builder;

@Builder
public record OrderDTO(
    Integer id,
    String customerName,
    Double cost,
    Double weight,
    ShippingType shippingType,
    String destination
) {
    public static OrderDTO OrdertoDTO(Order order) {
        return OrderDTO.builder()
            .id(order.getId())
            .customerName(order.getCustomerName())
            .cost(order.getCost())
            .build();
    }
}
