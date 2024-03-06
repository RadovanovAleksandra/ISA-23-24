package com.isa.medical_equipment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String role;
}
