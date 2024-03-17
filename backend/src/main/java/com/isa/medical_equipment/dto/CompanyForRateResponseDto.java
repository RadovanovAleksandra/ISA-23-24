package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class CompanyForRateResponseDto {
    private long id;
    private String name;

    public CompanyForRateResponseDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
