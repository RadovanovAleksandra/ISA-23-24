package com.isa.medical_equipment.services.implementation;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.isa.medical_equipment.config.email.ReservationCreatedEmailContext;
import com.isa.medical_equipment.entity.Reservation;
import com.isa.medical_equipment.entity.User;
import com.isa.medical_equipment.services.interfaces.BaseEmailService;
import com.isa.medical_equipment.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final BaseEmailService emailService;

    public void sendReservationCreatedEmail(Reservation reservation, User user) {
        var emailContext = new ReservationCreatedEmailContext();
        emailContext.init(user);
        emailContext.setReservation(reservation);
        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode("http://localhost:3000/reservation/" + reservation.getId(), BarcodeFormat.QR_CODE, 500, 500);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageConfig config = new MatrixToImageConfig();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream, config);
            byte[] pngData = pngOutputStream.toByteArray();

            emailService.sendEmailWithAttachment(emailContext, new ByteArrayResource(pngData));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
