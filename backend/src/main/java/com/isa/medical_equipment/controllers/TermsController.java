package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.IrregularTermRequestDto;
import com.isa.medical_equipment.dto.TermResponseDto;
import com.isa.medical_equipment.entity.IrregularTermStatusEnum;
import com.isa.medical_equipment.entity.Term;
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
    private final TermsRepository termsRepository;

    @PostMapping("irregular")
    public ResponseEntity<?> createIrregularTerm(@RequestBody IrregularTermRequestDto request) {
        var companyOpt =companyRepository.findById(request.getCompanyId());
        if (companyOpt.isEmpty()){
            var dto = new CommonResponseDto();
            dto.setMessage("Company not found");
            return ResponseEntity.badRequest().body(dto);
        }

        if (request.getTimeStamp().toLocalTime().isAfter(companyOpt.get().getWorkingHoursStart()) &&
                request.getTimeStamp().toLocalTime().plusMinutes(request.getDurationInMinutes()).isAfter(companyOpt.get().getWorkingHoursStart()) &&
                request.getTimeStamp().toLocalTime().isBefore(companyOpt.get().getWorkingHoursEnd()) &&
                request.getTimeStamp().toLocalTime().plusMinutes(request.getDurationInMinutes()).isBefore(companyOpt.get().getWorkingHoursEnd())) {

            var term = new Term();
            term.setIrregular(true);
            term.setStatus(IrregularTermStatusEnum.PENDING);
            term.setCompany(companyOpt.get());
            term.setStart(request.getTimeStamp());
            term.setDurationInMinutes(request.getDurationInMinutes());
            //TODO: add Admin
            termsRepository.save(term);
            return ResponseEntity.ok(new TermResponseDto(term.getId(), term.getStart(), term.getDurationInMinutes()));
        } else {
            var dto = new CommonResponseDto();
            dto.setMessage("You have to respect company's working hours");
            return ResponseEntity.badRequest().body(dto);
        }
    }
}
