package com.isa.medical_equipment.dto;

import com.isa.medical_equipment.entity.RoleEnum;
import lombok.Data;

@Data
public class ProfilePutRequestDto {
    private String name;
    private String lastName;
    private RoleEnum role;
    private String city;
    private String state;
    private String phone;
    private String profession;
}
