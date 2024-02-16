package com.isa.medical_equipment.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "terms")
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    private LocalDateTime start;
    private int durationInMinutes;
    private boolean irregular;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private IrregularTermStatusEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    Company company;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "reservation_id",
            nullable = true
    )
    Reservation reservation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "admin_id",
            nullable = false
    )
    User admin;

}
