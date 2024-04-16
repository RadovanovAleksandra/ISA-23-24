package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class EquipmentResponseDto {
    private long id;
    private String name;
    private int availableQuantity;
    private int price;

    public EquipmentResponseDto(long id, String name, int availableQuantity, int price) {
        this.id = id;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }
}
