package com.isa.medical_equipment.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "equipments")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long id;

    private String name;
    private int availableQuantity;
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    Company company;
}
