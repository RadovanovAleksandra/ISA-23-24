package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class EquipmentResponseDto {
    private long id;
    private String name;
    private int availableQuantity;

    public EquipmentResponseDto(long id, String name, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.availableQuantity = availableQuantity;
    }
}
