package com.isa.medical_equipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TermResponseDto {
    private long id;
    private LocalDateTime timestamp;
    private int durationInMinutes;

    public TermResponseDto(long id, LocalDateTime timestamp,int durationInMinutes) {
        this.id = id;
        this.timestamp = timestamp;
        this.durationInMinutes = durationInMinutes;
    }
}
