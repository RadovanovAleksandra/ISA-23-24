package com.isa.medical_equipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PenaltyResponseDto {
    private long id;
    private LocalDateTime timestamp;

    public PenaltyResponseDto(long id, LocalDateTime timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
}
