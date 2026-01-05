package com.example.logistics_api.DTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Integer Id;
    private String customerName;
    private Integer cost;
}
