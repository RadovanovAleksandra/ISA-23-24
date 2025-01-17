package com.isa.medical_equipment.config.email;

import com.isa.medical_equipment.entity.Reservation;
import com.isa.medical_equipment.entity.User;

public class ReservationCreatedEmailContext extends AbstractEmailContext {
    @Override
    public <T> void init(T context) {
        User customer = (User) context;
        put("firstName", customer.getName());
        setTemplateLocation("email/reservation-created-template");
        setSubject("Reservation created");
        setFrom("no-reply@medical-equipment.com");
        setTo(customer.getEmail());
    }


    public void setData(String user) {
        put("userName", user);
    }
}
