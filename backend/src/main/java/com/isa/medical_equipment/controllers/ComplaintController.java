package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.ComplaintAnswerRequestDto;
import com.isa.medical_equipment.dto.ComplaintResponseDto;
import com.isa.medical_equipment.dto.CreateComplaintRequestDto;
import com.isa.medical_equipment.entity.Complaint;
import com.isa.medical_equipment.repositories.CompanyRepository;
import com.isa.medical_equipment.repositories.ComplaintRepository;
import com.isa.medical_equipment.repositories.ReservationRepository;
import com.isa.medical_equipment.repositories.UserRepository;
import com.isa.medical_equipment.security.UserDetailsImpl;
import com.isa.medical_equipment.services.interfaces.EmailService;
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
import java.util.ArrayList;
import java.util.Collection;

@CrossOrigin(origins = "*")
@RequestMapping("/api/complaints")
@RestController
@RequiredArgsConstructor
@Tag(name = "Complaints", description = "Controller for managing complaints")
public class ComplaintController {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ComplaintRepository complaintRepository;
    private final EmailService emailService;

    @PostMapping
    @Operation(summary = "Create new complaint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a complaint"),
            @ApiResponse(responseCode = "400", description = "Failed to create a complaint")
    })
    public ResponseEntity<?> createComplaint(@RequestBody CreateComplaintRequestDto requestBody) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId()).get();

        if (requestBody.getAdminId() != null && requestBody.getCompanyId() != null) {
            var dto = new CommonResponseDto();
            dto.setMessage("You have to pick at least one subject to write complaint for");
            return ResponseEntity.badRequest().body(dto);
        }

        if (requestBody.getAdminId() != null) {
            var adminOpt = userRepository.findById(requestBody.getAdminId());
            if (adminOpt.isEmpty()) {
                var dto = new CommonResponseDto();
                dto.setMessage("Company admin is not found");
                return ResponseEntity.badRequest().body(dto);
            }

            var admin = adminOpt.get();

            var reservations = reservationRepository.findByUser(user);
            if (reservations.stream().noneMatch(x -> x.getTerm().getAdmin().equals(admin))) {
                var dto = new CommonResponseDto();
                dto.setMessage("You are not allowed to write complaint about this administrator");
                return ResponseEntity.badRequest().body(dto);
            }
            var complaint = new Complaint();
            complaint.setCustomer(user);
            complaint.setCompanyAdmin(admin);
            complaint.setText(requestBody.getText());
            complaint.setTimestamp(LocalDateTime.now());
            complaintRepository.save(complaint);
        } else {
            var companyOpt = companyRepository.findById(requestBody.getCompanyId());
            if (companyOpt.isEmpty()) {
                var dto = new CommonResponseDto();
                dto.setMessage("Company is not found");
                return ResponseEntity.badRequest().body(dto);
            }

            var company = companyOpt.get();

            var reservations = reservationRepository.findByUser(user);
            if (reservations.stream().noneMatch(x -> x.getTerm().getCompany().equals(company))) {
                var dto = new CommonResponseDto();
                dto.setMessage("You are not allowed to write complaint about this company");
                return ResponseEntity.badRequest().body(dto);
            }
            var complaint = new Complaint();
            complaint.setCompany(company);
            complaint.setCustomer(user);
            complaint.setText(requestBody.getText());
            complaint.setTimestamp(LocalDateTime.now());
            complaintRepository.save(complaint);

        }
        return ResponseEntity.ok().build();
    }

        @PostMapping("answer")
        @Operation(summary = "Answer to the complaint")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully answered to the complaint"),
                @ApiResponse(responseCode = "400", description = "Failed to answered to the complaint")
        })
        public ResponseEntity<?> answerComplaint(@RequestBody ComplaintAnswerRequestDto requestBody) {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId());

        var complaintOpt = complaintRepository.findById(requestBody.getId());
        if (complaintOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("Complaint is not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var complaint = complaintOpt.get();
        complaint.setAnswer(requestBody.getAnswer());
        complaint.setAdmin(user.get());
        complaintRepository.save(complaint);

        emailService.sendComplaintAnsweredEmail(complaint.getCustomer());

        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/for-user")
    @Operation(summary = "Get list of complaints for regular user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched data"),
            @ApiResponse(responseCode = "400", description = "Failed to fetch data")
    })
    public ResponseEntity<Collection<ComplaintResponseDto>> getComplaintsForUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId());

        var complaints = complaintRepository.findByCustomer(user.get());
        var dtos = new ArrayList<ComplaintResponseDto>();
        for (var complaint : complaints) {
            var dto = new ComplaintResponseDto();
            dto.setText(complaint.getText());
            dto.setId(complaint.getId());
            dto.setAnswer(complaint.getAnswer());
            dto.setTimestamp(complaint.getTimestamp());
            dto.setCompanyAdminName(complaint.getCompanyAdmin() != null ? complaint.getCompanyAdmin().getName() : null);
            dto.setCompanyName(complaint.getCompany() != null ? complaint.getCompany().getName() : null);

            dtos.add(dto);
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/for-admin")
    @Operation(summary = "Get list of complaints for admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched data"),
            @ApiResponse(responseCode = "400", description = "Failed to fetch data")
    })
    public ResponseEntity<Collection<ComplaintResponseDto>> getComplaintsForAdmin() {
        SecurityContext context = SecurityContextHolder.getContext();
        var authUser = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        var user = userRepository.findById(authUser.getId());

        var complaints = complaintRepository.findByAnswer(null);
        var dtos = new ArrayList<ComplaintResponseDto>();
        for (var complaint : complaints) {
            var dto = new ComplaintResponseDto();
            dto.setText(complaint.getText());
            dto.setId(complaint.getId());
            dto.setAnswer(complaint.getAnswer());
            dto.setTimestamp(complaint.getTimestamp());
            dto.setCompanyAdminName(complaint.getCompanyAdmin() != null ? complaint.getCompanyAdmin().getName() : null);
            dto.setCompanyName(complaint.getCompany() != null ? complaint.getCompany().getName() : null);
            dto.setUserName(complaint.getCustomer().getName() + " " + complaint.getCustomer().getLastName());

            dtos.add(dto);
        }
        return ResponseEntity.ok(dtos);
    }

}
