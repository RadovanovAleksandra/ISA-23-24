package com.isa.medical_equipment.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Long id;
    private String text;
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "company_admin_id",
            nullable = true
    )
    private User companyAdmin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "company_id",
            nullable = true
    )
    Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "admin_id",
            nullable = true
    )
    private User admin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private User customer;
}
