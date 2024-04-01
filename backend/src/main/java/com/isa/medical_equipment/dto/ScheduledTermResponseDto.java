package com.isa.medical_equipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduledTermResponseDto {
    private long id;
    private String companyName;
    private LocalDateTime termStart;
    private int duration;
    private boolean cancellable;
}
