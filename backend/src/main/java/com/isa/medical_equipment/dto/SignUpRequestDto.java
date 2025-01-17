package com.isa.medical_equipment.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String state;
    private String phone;
    private String profession;
}
