package com.example.logistics_api.DTO;

import com.example.logistics_api.Enumeration.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {
    private VehicleType type;
    private Integer orderId;
}
