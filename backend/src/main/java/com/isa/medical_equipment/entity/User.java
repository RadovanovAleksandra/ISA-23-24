package com.isa.medical_equipment.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;
    @Column(name = "password")
    private String password;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "phone")
    private String phone;
    @Column(name = "points")
    private int points;
    @Column(name = "profession")
    private String profession;
    @Column(name = "verified")
    private boolean verified;
    @ManyToMany(mappedBy = "admins")
    private Collection<Company> companies = new ArrayList<>();
    @ManyToOne
    @JoinColumn(
            name = "loyalty_program_id",
            referencedColumnName = "id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "FK_USER_LOYALTY_PROGRAM"
            )
    )
    private LoyaltyProgram loyaltyProgram;
}
