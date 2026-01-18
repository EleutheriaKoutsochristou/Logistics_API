package com.example.logistics_api.DTO;

public record OrderDto(
        Integer Id,
        String customerName,
        Integer cost
) {
}
