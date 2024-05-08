package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.*;
import com.isa.medical_equipment.entity.LoyaltyProgram;
import com.isa.medical_equipment.entity.Penalty;
import com.isa.medical_equipment.entity.ReservationStatusEnum;
import com.isa.medical_equipment.repositories.*;
import com.isa.medical_equipment.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RequestMapping("/api/terms")
@RestController
@RequiredArgsConstructor
@Tag(name = "Terms", description = "Controller for managing terms")
public class TermsController {

    private final CompanyRepository companyRepository;
    private final TermsRepository termsRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationItemRepository reservationItemRepository;
    private final UserRepository userRepository;
    private final PenaltyRepository penaltyRepository;
    private final LoyaltyProgramRepository loyaltyProgramRepository;

    @GetMapping("/pending/for-user")
    @Operation(summary = "Get terms user tried to book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched data successfully"),
    })
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

    @Transactional
    @PostMapping("{termId}/cancel")
    @Operation(summary = "Term cancellation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully cancelled a term"),
            @ApiResponse(responseCode = "400", description = "Failed to cancel a term")
    })
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
        termsRepository.save(term);
        var items = reservationItemRepository.findByReservation(reservation);
        for( var item : items) {
            reservationItemRepository.delete(item);
        }
        reservationRepository.delete(reservation);

        var penalty = new Penalty();
        penalty.setUser(user);
        penalty.setTimestamp(LocalDateTime.now());
        penaltyRepository.save(penalty);

        if (user.getLoyaltyProgram() != null) {
            user.setPoints(user.getPoints() - user.getLoyaltyProgram().getOnCancel());
            if (user.getPoints() < user.getLoyaltyProgram().getMinNumberOfPoints()) {
                user.setLoyaltyProgram(null);
                var loyaltyPrograms = loyaltyProgramRepository.findAll();
                Collections.sort(loyaltyPrograms, Comparator.comparingInt(LoyaltyProgram::getMinNumberOfPoints).reversed());
                for (var loyProg : loyaltyPrograms) {
                    if (user.getPoints() > loyProg.getMinNumberOfPoints()) {
                        user.setLoyaltyProgram(loyProg);
                        break;
                    }
                }
            }
            userRepository.save(user);
        }

        return ResponseEntity.ok().build();
    }

}
