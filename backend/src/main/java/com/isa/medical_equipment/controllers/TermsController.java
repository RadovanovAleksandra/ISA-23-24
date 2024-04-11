package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.*;
import com.isa.medical_equipment.entity.ReservationStatusEnum;
import com.isa.medical_equipment.repositories.CompanyRepository;
import com.isa.medical_equipment.repositories.ReservationRepository;
import com.isa.medical_equipment.repositories.TermsRepository;
import com.isa.medical_equipment.repositories.UserRepository;
import com.isa.medical_equipment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RequestMapping("/api/terms")
@RestController
@RequiredArgsConstructor
public class TermsController {

    private final CompanyRepository companyRepository;
    private final TermsRepository termsRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @GetMapping("/pending/for-user")
    public ResponseEntity<?> getListOfSuccessfulReservations() {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var reservations = reservationRepository.findByUserAndStatus(user, ReservationStatusEnum.PENDING);
        return ResponseEntity.ok( reservations.stream().map(x -> {
            var dto = new ScheduledTermResponseDto();
            dto.setId(x.getTerm().getId());
            dto.setDuration(x.getTerm().getDurationInMinutes());
            dto.setCompanyName(x.getTerm().getCompany().getName());
            dto.setTermStart(x.getTerm().getStart());
            dto.setCancellable(x.getTerm().getStart().minusMonths(1).isAfter(LocalDateTime.now()));
            return dto;
        }).collect(Collectors.toList()));
    }

    @PostMapping("{termId}/cancel")
    public ResponseEntity<?> createIrregularTerm(@PathVariable long termId) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var termOpt = termsRepository.findById(termId);
        if (termOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("Term not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var term = termOpt.get();
        if (term.getReservation() == null) {
            var dto = new CommonResponseDto();
            dto.setMessage("Term is not cancellable");
            return ResponseEntity.badRequest().body(dto);
        }

        if (!Objects.equals(term.getReservation().getUser().getId(), user.getId())) {
            var dto = new CommonResponseDto();
            dto.setMessage("You are not allowed to cancel different user's term");
            return ResponseEntity.badRequest().body(dto);
        }

        if (term.getStart().minusMonths(1).isBefore(LocalDateTime.now())) {
            var dto = new CommonResponseDto();
            dto.setMessage("You are not allowed to cancel term scheduled in less then one month in the future");
            return ResponseEntity.badRequest().body(dto);
        }

        var reservation = term.getReservation();
        term.setReservation(null);
        reservationRepository.delete(reservation);
        termsRepository.save(term);

        return ResponseEntity.ok().build();
    }

}
