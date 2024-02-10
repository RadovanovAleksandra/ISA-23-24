package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.Penalty;
import com.isa.medical_equipment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    Collection<Penalty> findByUser(User user);
}
