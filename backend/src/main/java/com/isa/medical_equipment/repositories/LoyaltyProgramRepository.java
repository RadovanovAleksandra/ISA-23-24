package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long> {

    int countByName(String name);

    Optional<LoyaltyProgram> findByName(String name);
}
