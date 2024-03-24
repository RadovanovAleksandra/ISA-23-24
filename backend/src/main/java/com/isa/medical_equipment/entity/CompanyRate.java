package com.isa.medical_equipment.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "company_rate")
public class CompanyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte rate;
    private String comment;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "FK_USER_COMPANY_RATE"
            )
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "company_id",
            referencedColumnName = "company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "FK_COMPANY_COMPANY_RATE"
            )
    )
    private Company company;
}
