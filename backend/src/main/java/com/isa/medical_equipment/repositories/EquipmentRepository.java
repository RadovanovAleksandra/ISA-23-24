package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.Company;
import com.isa.medical_equipment.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Collection<Equipment> findByCompany(Company company);
}
