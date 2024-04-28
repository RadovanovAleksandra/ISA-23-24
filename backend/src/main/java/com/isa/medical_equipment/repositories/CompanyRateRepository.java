package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.Company;
import com.isa.medical_equipment.entity.CompanyRate;
import com.isa.medical_equipment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CompanyRateRepository extends JpaRepository<CompanyRate, Long> {
    Optional<CompanyRate> findByUserAndCompany(User user, Company company);
    Collection<CompanyRate> findByCompany(Company company);
}
