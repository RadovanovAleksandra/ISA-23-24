package com.isa.medical_equipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintResponseDto {
    private Long id;
    private String text;
    private String answer;
    private LocalDateTime timestamp;
    private String companyName;
    private String companyAdminName;
}
