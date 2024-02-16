package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.CompanyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRateRepository extends JpaRepository<CompanyRate, Long> {
}
