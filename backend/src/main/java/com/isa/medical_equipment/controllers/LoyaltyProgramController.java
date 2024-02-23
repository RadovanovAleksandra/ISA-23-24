package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.LoyaltyProgramCreateRequestDto;
import com.isa.medical_equipment.dto.LoyaltyProgramUpdateRequestDto;
import com.isa.medical_equipment.entity.LoyaltyProgram;
import com.isa.medical_equipment.repositories.LoyaltyProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api/loyalty-programs")
@RestController
@RequiredArgsConstructor
public class LoyaltyProgramController {

    private final LoyaltyProgramRepository loyaltyProgramRepository;
    @PostMapping
    public ResponseEntity<?> createProgram(@RequestBody LoyaltyProgramCreateRequestDto request) {
        var number = loyaltyProgramRepository.countByName(request.getName());
        if (number > 0) {
            var dto = new CommonResponseDto();
            dto.setMessage("Program with this name already exists");
            return ResponseEntity.badRequest().body(dto);
        }

        var program = new LoyaltyProgram();
        BeanUtils.copyProperties(request, program);
        loyaltyProgramRepository.save(program);
        return ResponseEntity.ok(program);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateLoyaltyProgram(@PathVariable long id, @RequestBody LoyaltyProgramUpdateRequestDto request) {
        var loyaltyProgramOpt = loyaltyProgramRepository.findById(id);
        if (loyaltyProgramOpt.isEmpty()) {
            var dto = new CommonResponseDto();
            dto.setMessage("Loyalty program is not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var existingProgramWithTheSameId = loyaltyProgramRepository.findByName(request.getName()).get();
        if (existingProgramWithTheSameId.getId() != id) {
            var dto = new CommonResponseDto();
            dto.setMessage("You cannot use provided name");
            return ResponseEntity.badRequest().body(dto);
        }

        var program = loyaltyProgramOpt.get();
        BeanUtils.copyProperties(request, program);
        loyaltyProgramRepository.save(program);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLoyaltyProgram(@PathVariable long id) {
        loyaltyProgramRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
