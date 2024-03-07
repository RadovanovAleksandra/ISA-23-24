package com.isa.medical_equipment.dto;

import com.isa.medical_equipment.entity.RoleEnum;
import lombok.Data;


@Data
public class ProfileResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private RoleEnum role;
    private String city;
    private String state;
    private String phone;
    private String profession;
    private int points;
    private int penalties;
    private String loyaltyProgram;
}
