package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class LoyaltyProgramCreateRequestDto {
    String name;
    int onReservation;
    int onCancel;
    int minNumberOfPoints;
    double discountRate;
}
