package com.isa.medical_equipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TermResponseDto {
    private long id;
    private LocalDateTime timestamp;

    public TermResponseDto(long id, LocalDateTime timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
}
