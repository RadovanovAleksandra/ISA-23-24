package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.LoyaltyProgramCreateRequestDto;
import com.isa.medical_equipment.dto.LoyaltyProgramUpdateRequestDto;
import com.isa.medical_equipment.entity.LoyaltyProgram;
import com.isa.medical_equipment.repositories.LoyaltyProgramRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*")
@RequestMapping("/api/loyalty-programs")
@RestController
@RequiredArgsConstructor
@Tag(name = "Loyalty program", description = "Controller for managing loyalty programs")
public class LoyaltyProgramController {

    private final LoyaltyProgramRepository loyaltyProgramRepository;

    @GetMapping
    @Operation(summary = "Get list of available loyalty programs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched data"),
            @ApiResponse(responseCode = "400", description = "Failed to fetch data")
    })
    public ResponseEntity<Collection<LoyaltyProgram>> findAll() {
        return ResponseEntity.ok(loyaltyProgramRepository.findAll());
    }
    @PostMapping
    @Operation(summary = "Create new loyalty program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a program"),
            @ApiResponse(responseCode = "400", description = "Failed to create a program")
    })
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
    @Operation(summary = "Update loyalty program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a program"),
            @ApiResponse(responseCode = "400", description = "Failed to update a program")
    })
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
    @Operation(summary = "Delete loyalty program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a program"),
    })
    public ResponseEntity<?> deleteLoyaltyProgram(@PathVariable long id) {
        loyaltyProgramRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
