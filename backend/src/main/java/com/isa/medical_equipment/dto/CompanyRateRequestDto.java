package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class CompanyRateRequestDto {
    private long companyId;
    private String comment;
    private byte rate;
}
