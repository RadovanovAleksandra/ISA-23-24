package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class LoyaltyProgramUpdateRequestDto {
    long id;
    String name;
    int newPoints;
    int minNumberOfPoints;
    double discountRate;
}
