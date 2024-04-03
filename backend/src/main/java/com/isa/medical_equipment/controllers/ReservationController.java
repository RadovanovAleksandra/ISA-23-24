package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.ReservationRequestDto;
import com.isa.medical_equipment.dto.ReservationResponseDto;
import com.isa.medical_equipment.entity.Reservation;
import com.isa.medical_equipment.entity.ReservationItem;
import com.isa.medical_equipment.entity.ReservationStatusEnum;
import com.isa.medical_equipment.repositories.*;
import com.isa.medical_equipment.security.UserDetailsImpl;
import com.isa.medical_equipment.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RequestMapping("/api/reservations")
@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationItemRepository reservationItemRepository;
    private final EquipmentRepository equipmentRepository;
    private final PenaltyRepository penaltyRepository;
    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<?> getListForUserByStatus(@RequestParam ReservationStatusEnum status) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var reservations = reservationRepository.findByUserAndStatus(user, status);
        return ResponseEntity.ok( reservations.stream().map(x -> {
            var dto = new ReservationResponseDto();
            dto.setId(x.getId());
            dto.setDuration(x.getTerm().getDurationInMinutes());
            dto.setCreatedAt(x.getTimestamp());
            dto.setCompanyName(x.getTerm().getCompany().getName());
            dto.setTermStart(x.getTerm().getStart());
            dto.setPrice(x.getPrice());
            return dto;
        }).collect(Collectors.toList()));
    }

    @GetMapping("/successful")
    public ResponseEntity<?> getListOfSuccessfulReservations() {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var reservations = reservationRepository.findByUserAndStatus(user, ReservationStatusEnum.ACCEPTED);
        return ResponseEntity.ok( reservations.stream().map(x -> {
            var dto = new ReservationResponseDto();
            dto.setId(x.getId());
            dto.setDuration(x.getTerm().getDurationInMinutes());
            dto.setCreatedAt(x.getTimestamp());
            dto.setCompanyName(x.getTerm().getCompany().getName());
            dto.setTermStart(x.getTerm().getStart());
            dto.setPrice(x.getPrice());
            return dto;
        }).collect(Collectors.toList()));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDto request) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var penalties = penaltyRepository.findByUser(user);
        if (penalties.size() >= 3) {
            var dto = new CommonResponseDto();
            dto.setMessage("You are not allowed to create reservations because you have 3 or more penalties");
            return ResponseEntity.badRequest().body(dto);
        }

        var termOpt = termsRepository.findById(request.getTermId());
        if (termOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("Term is not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var term = termOpt.get();
        if (term.getReservation() != null) {
            var dto = new CommonResponseDto();
            dto.setMessage("Term is not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var reservation = new Reservation();
        reservation.setTerm(term);
        reservation.setUser(user);
        reservation.setTimestamp(LocalDateTime.now());
        reservation.setStatus(ReservationStatusEnum.PENDING);
        reservationRepository.save(reservation);

        for (var item : request.getReservationItems()) {
            var reservationItem = new ReservationItem();
            reservationItem.setReservation(reservation);
            reservationItem.setQuantity(item.getQuantity());
            var equipment = equipmentRepository.findById(item.getEquipmentId());
            if (equipment.isEmpty()) {
                var dto = new CommonResponseDto();
                dto.setMessage("Equipment with id " + item.getEquipmentId() + " is not found");
                return ResponseEntity.badRequest().body(dto);
            }
            reservationItem.setEquipment(equipment.get());
            reservationItemRepository.save(reservationItem);
        }

        emailService.sendReservationCreatedEmail(reservation, user);

        return ResponseEntity.ok().build();
    }
}
