package com.isa.medical_equipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IrregularTermRequestDto {
    private LocalDateTime timeStamp;
    private int durationInMinutes;
    private long companyId;
}
