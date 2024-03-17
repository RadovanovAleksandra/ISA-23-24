package com.isa.medical_equipment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintResponseDto {
    private Long id;
    private String text;
    private String answer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private String companyName;
    private String companyAdminName;
    private String userName;
}
