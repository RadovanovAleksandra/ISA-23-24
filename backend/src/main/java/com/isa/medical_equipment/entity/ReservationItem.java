package com.isa.medical_equipment.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reservation_items")
public class ReservationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_item_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(
            name = "reservation_id",
            referencedColumnName = "reservation_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "FK_RESERVATION_RESERVATION_ITEM"
            )
    )
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(
            name = "equipment_id",
            referencedColumnName = "equipment_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "FK_EQUIPMENT_RESERVATION_ITEM"
            )
    )
    private Equipment equipment;

    private int quantity;
}
