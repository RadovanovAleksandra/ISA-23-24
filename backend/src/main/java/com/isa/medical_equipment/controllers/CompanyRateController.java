package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.CompanyRateRequestDto;
import com.isa.medical_equipment.entity.CompanyRate;
import com.isa.medical_equipment.repositories.CompanyRateRepository;
import com.isa.medical_equipment.repositories.CompanyRepository;
import com.isa.medical_equipment.repositories.ReservationRepository;
import com.isa.medical_equipment.repositories.UserRepository;
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

import java.time.LocalDateTime;

@CrossOrigin(origins = "*")
@RequestMapping("/api/company-rates")
@RestController
@RequiredArgsConstructor
@Tag(name = "Company Rates", description = "Controller for managing company rates")
public class CompanyRateController {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CompanyRateRepository companyRateRepository;
    private final ReservationRepository reservationRepository;


    @PostMapping
    @Operation(summary = "Add new rate for company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully save a rate"),
            @ApiResponse(responseCode = "400", description = "Failed to save a rate")
    })
    public ResponseEntity<?> saveCompanyRate(@RequestBody CompanyRateRequestDto request) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        var companyOpt = companyRepository.findById(request.getCompanyId());
        if (companyOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("Company is not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var company = companyOpt.get();
        var reservations = reservationRepository.findByUser(user);
        if (reservations.stream().noneMatch(x -> x.getTerm().getCompany().equals(company))) {
            var dto = new CommonResponseDto();
            dto.setMessage("You have no reservations in this company");
            return ResponseEntity.badRequest().body(dto);
        }

        var companyRateOpt = companyRateRepository.findByUserAndCompany(user, company);
        if (companyRateOpt.isEmpty()) {
            var newRate = new CompanyRate();
            newRate.setCompany(company);
            newRate.setComment(request.getComment());
            newRate.setRate(request.getRate());
            newRate.setUser(user);
            newRate.setTimestamp(LocalDateTime.now());
            companyRateRepository.save(newRate);
        } else {
            var rate = companyRateOpt.get();
            rate.setRate(request.getRate());
            rate.setComment(request.getComment());
            rate.setTimestamp(LocalDateTime.now());
            companyRateRepository.save(rate);
        }

        return ResponseEntity.noContent().build();
    }
}
