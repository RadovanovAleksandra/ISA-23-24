package com.isa.medical_equipment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponseDto {
    private long id;
    private LocalDateTime createdAt;
    private String companyName;
    private LocalDateTime termStart;
    private int duration;
    private int price;

}
