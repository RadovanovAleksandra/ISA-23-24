package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class EquipmentResponseDto {
    private long id;
    private String name;

    public EquipmentResponseDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
