package com.isa.medical_equipment.entity;

import lombok.Data;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "secure_tokens")
public class SecureToken{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @Column(name = "timestamp")
    private Timestamp timeStamp;

    private LocalDateTime expireAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "FK_USER_SECURITY_TOKEN"
            )
    )
    private User user;
    @Transient
    private boolean isExpired;

    public boolean isExpired() {
        return getExpireAt().isBefore(LocalDateTime.now());
    }

}