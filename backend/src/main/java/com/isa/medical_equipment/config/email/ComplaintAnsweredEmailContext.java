package com.isa.medical_equipment.config.email;

import com.isa.medical_equipment.entity.User;

public class ComplaintAnsweredEmailContext extends AbstractEmailContext {
    @Override
    public <T> void init(T context) {
        User customer = (User) context;
        put("firstName", customer.getName());
        setTemplateLocation("email/complaint-answered-template");
        setSubject("Complaint answered");
        setFrom("no-reply@medical-equipment.com");
        setTo(customer.getEmail());
    }


    public void setData(String user) {
        put("userName", user);
    }
}
