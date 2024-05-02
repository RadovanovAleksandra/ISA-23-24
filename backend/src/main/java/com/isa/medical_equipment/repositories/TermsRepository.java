package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.Company;
import com.isa.medical_equipment.entity.Reservation;
import com.isa.medical_equipment.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface TermsRepository extends JpaRepository<Term, Long> {
    Collection<Term> findByCompanyAndReservationAndStartGreaterThanEqual(Company company, Reservation reservation, LocalDateTime start);
    Collection<Term> findByCompany(Company company);
    Collection<Term> findByCompanyAndReservationIsNotNull(Company company);
}
