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

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "customer_id",
            nullable = true
    )
    User customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "admin_id",
            nullable = false
    )
    User admin;

}
