package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.Complaint;
import com.isa.medical_equipment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Collection<Complaint> findByCustomer(User customer);
}
