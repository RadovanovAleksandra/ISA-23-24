package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.CompanyResponseDto;
import com.isa.medical_equipment.dto.EquipmentResponseDto;
import com.isa.medical_equipment.repositories.CompanyRepository;
import com.isa.medical_equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RequestMapping("/api/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final EquipmentRepository equipmentRepository;
    private final CompanyRepository companyRepository;

    @GetMapping()
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.ok(companyRepository.findAll().stream().map(x -> new CompanyResponseDto(x.getId(), x.getName(), x.getCity(), x.getAddress(), x.getRating())).collect(Collectors.toList()));
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
        var dtos = equipmentRepository.findByCompany(company).stream().map(x -> new EquipmentResponseDto(x.getId(), x.getName()));
        return ResponseEntity.ok(dtos);
    }
}
