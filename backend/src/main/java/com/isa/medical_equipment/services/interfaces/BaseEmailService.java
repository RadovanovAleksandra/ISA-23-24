package com.isa.medical_equipment.services.interfaces;

import com.isa.medical_equipment.config.email.AbstractEmailContext;
import org.springframework.core.io.ByteArrayResource;

public interface BaseEmailService {
    void sendEmailWithAttachment(AbstractEmailContext email, ByteArrayResource attachment);
}
