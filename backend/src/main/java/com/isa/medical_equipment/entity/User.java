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
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;
    @Column(name = "password")
    private String password;

    @ManyToMany(mappedBy = "admins")
    private Collection<Company> companies = new ArrayList<>();
}
