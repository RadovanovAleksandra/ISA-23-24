package com.isa.medical_equipment.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ReservationRequestDto {
    Collection<ReservationItemRequestDto> reservationItems;
    private long termId;
}

