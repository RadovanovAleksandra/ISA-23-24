package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class CompanyResponseDto {
    private Long id;
    private String name;
    private String city;
    private String address;
    private byte rating;

    public CompanyResponseDto(Long id, String name, String city, String address, byte rating) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
    }
}
