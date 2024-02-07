package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> {
    SecureToken findByToken(String token);
    void removeByToken(String token);
}
