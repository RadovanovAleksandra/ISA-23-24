package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class LoyaltyProgramCreateRequestDto {
    String name;
    int newPoints;
    int minNumberOfPoints;
    double discountRate;
}
