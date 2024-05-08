package com.isa.medical_equipment.services.implementation;

import com.isa.medical_equipment.config.email.AbstractEmailContext;
import com.isa.medical_equipment.services.interfaces.BaseEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

@Service
@RequiredArgsConstructor
public class BaseEmailServiceImpl implements BaseEmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    @Override
    public void sendEmailWithAttachment(AbstractEmailContext email, ByteArrayResource attachment) {
        Context context = new Context();
        context.setVariables(email.getContext());
        String emailContent = templateEngine.process(email.getTemplateLocation(), context);

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
            mimeMessage.setFrom(new InternetAddress(email.getFrom()));
            mimeMessage.setSubject(email.getSubject());
            mimeMessage.setContent(emailContent, "text/html; charset=utf-8");

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setText(emailContent);
            helper.setSubject(email.getSubject());
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            if (attachment != null)
                helper.addAttachment("qr.jpg", attachment);

        };

        emailSender.send(preparator);
    }
}
