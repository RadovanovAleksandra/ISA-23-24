package com.isa.medical_equipment.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class CompanyResponseDto {
    private Long id;
    private String name;
    private String city;
    private String address;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;

    public CompanyResponseDto(Long id, String name, String city, String address, LocalTime workingHoursStart, LocalTime workingHoursEnd) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
    }
}
