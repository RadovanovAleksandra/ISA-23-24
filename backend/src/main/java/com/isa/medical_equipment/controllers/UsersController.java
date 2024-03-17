package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CompanyAdminForRateResponseDto;
import com.isa.medical_equipment.repositories.ReservationRepository;
import com.isa.medical_equipment.repositories.UserRepository;
import com.isa.medical_equipment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    @GetMapping("/company-admins/for-user")
    public ResponseEntity<?> getCompaniesWhichUserCanRate() {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var reservations = reservationRepository.findByUser(user);
        var admins = reservations.stream()
                .map(reservation -> reservation.getTerm().getAdmin())
                .distinct()
                .toList();

        var dtos = admins.stream().map(x -> new CompanyAdminForRateResponseDto(x.getId(), x.getName()));
        return ResponseEntity.ok(dtos);
    }
}
