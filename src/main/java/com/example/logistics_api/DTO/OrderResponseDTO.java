package com.example.logistics_api.DTO;

import com.example.logistics_api.Entity.Order;
import com.example.logistics_api.Enumeration.ShippingType;
import com.example.logistics_api.Enumeration.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.logistics_api.Enumeration.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    Integer id;
    String customerName;
    Double cost;
    Double weight;
    ShippingType shippingType;
    String destination;

    public static Object fromEntity(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerName(),
                order.getCost(),
                order.getWeight(),
                order.getShippingType(),
                order.getDestination()
        );
    }
}
