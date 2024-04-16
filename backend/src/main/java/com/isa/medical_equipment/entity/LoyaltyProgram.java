package com.isa.medical_equipment.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "loyalty_programs")
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    int onReservation;
    int onCancel;
    int minNumberOfPoints;
    double discountRate;

}
