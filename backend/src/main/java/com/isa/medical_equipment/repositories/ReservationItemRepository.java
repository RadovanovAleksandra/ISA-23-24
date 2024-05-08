package com.isa.medical_equipment.repositories;

import com.isa.medical_equipment.entity.Reservation;
import com.isa.medical_equipment.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {
    Collection<ReservationItem> findByReservation(Reservation reservation);
}
