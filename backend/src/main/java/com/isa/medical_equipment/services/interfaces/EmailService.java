package com.isa.medical_equipment.services.interfaces;

import com.isa.medical_equipment.entity.Reservation;
import com.isa.medical_equipment.entity.User;

public interface EmailService {
    void sendReservationCreatedEmail(Reservation reservation, User user);
}
