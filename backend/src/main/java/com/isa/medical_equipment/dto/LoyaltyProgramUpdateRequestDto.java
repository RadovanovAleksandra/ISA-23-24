package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class LoyaltyProgramUpdateRequestDto {
    long id;
    String name;
    int onReservation;
    int onCancel;
    int minNumberOfPoints;
    double discountRate;
}
