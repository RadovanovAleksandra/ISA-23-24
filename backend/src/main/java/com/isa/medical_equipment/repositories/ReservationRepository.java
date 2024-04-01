package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.Company;
import com.isa.medical_equipment.entity.Reservation;
import com.isa.medical_equipment.entity.ReservationStatusEnum;
import com.isa.medical_equipment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Collection<Reservation> findByUser(User user);

    Collection<Reservation> findByUserAndStatus(User user, ReservationStatusEnum status);
}
