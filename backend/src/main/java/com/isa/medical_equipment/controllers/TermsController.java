package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.EquipmentResponseDto;
import com.isa.medical_equipment.dto.TermResponseDto;
import com.isa.medical_equipment.repositories.CompanyRepository;
import com.isa.medical_equipment.repositories.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api/terms")
@RestController
@RequiredArgsConstructor
public class TermsController {

    private final CompanyRepository companyRepository;


}
