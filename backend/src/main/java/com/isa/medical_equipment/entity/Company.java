package com.isa.medical_equipment.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    private String name;
    private String city;
    private String address;
    private byte rating;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;

    @ManyToMany
    @JoinTable(
            name = "company_admins",
            joinColumns = @JoinColumn(
                    name = "company_id",
                    foreignKey = @ForeignKey
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    foreignKey = @ForeignKey
            )
    )
    private Collection<User> admins = new ArrayList<>();

}