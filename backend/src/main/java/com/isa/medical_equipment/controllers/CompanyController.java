package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.*;
import com.isa.medical_equipment.repositories.*;
import com.isa.medical_equipment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RequestMapping("/api/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final EquipmentRepository equipmentRepository;
    private final CompanyRepository companyRepository;
    private final TermsRepository termsRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @GetMapping()
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.ok(companyRepository.findAll().stream().map(x -> new CompanyResponseDto(x.getId(), x.getName(), x.getCity(), x.getAddress(), x.getWorkingHoursStart(), x.getWorkingHoursEnd())).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/equipment")
    public ResponseEntity<?> getEquipmentForCompany(@PathVariable long id) {
        var companyOpt =companyRepository.findById(id);
        if (companyOpt.isEmpty()){
            var dto = new CommonResponseDto();
            dto.setMessage("Company not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var company = companyOpt.get();
        var dtos = equipmentRepository.findByCompany(company).stream().map(x -> new EquipmentResponseDto(x.getId(), x.getName(), x.getAvailableQuantity(), x.getPrice()));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/terms")
    public ResponseEntity<?> getTermsForCompany(@PathVariable long id) {
        var companyOpt =companyRepository.findById(id);
        if (companyOpt.isEmpty()){
            var dto = new CommonResponseDto();
            dto.setMessage("Company not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var company = companyOpt.get();
        var dtos = termsRepository.findByCompanyAndReservation(company, null).stream().map(x -> new TermResponseDto(x.getId(), x.getStart(), x.getDurationInMinutes()));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/for-user")
    public ResponseEntity<?> getCompaniesWhichUserCanRate() {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var reservations = reservationRepository.findByUser(user);
        var companies = reservations.stream()
                .map(reservation -> reservation.getTerm().getCompany())
                .distinct()
                .toList();

        var dtos = companies.stream().map(x -> new CompanyForRateResponseDto(x.getId(), x.getName()));
        return ResponseEntity.ok(dtos);
    }
}
