package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class CreateComplaintRequestDto {
    String text;
    Long adminId;
    Long companyId;
}
