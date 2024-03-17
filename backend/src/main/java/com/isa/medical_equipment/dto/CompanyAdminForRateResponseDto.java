package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class CompanyAdminForRateResponseDto {
    private long id;
    private String name;

    public CompanyAdminForRateResponseDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
