package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class ReservationItemRequestDto {
    private long equipmentId;
    private int quantity;
}
