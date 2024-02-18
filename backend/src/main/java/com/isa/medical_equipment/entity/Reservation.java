package com.isa.medical_equipment.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private LocalDateTime timestamp;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ReservationStatusEnum status;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "FK_RESERVATION_USER"
            )
    )
    private User user;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Term term;
}
