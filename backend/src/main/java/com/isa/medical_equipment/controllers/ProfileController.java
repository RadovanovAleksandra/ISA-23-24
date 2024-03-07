package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.PenaltyResponseDto;
import com.isa.medical_equipment.dto.ProfilePutRequestDto;
import com.isa.medical_equipment.dto.ProfileResponseDto;
import com.isa.medical_equipment.repositories.LoyaltyProgramRepository;
import com.isa.medical_equipment.repositories.PenaltyRepository;
import com.isa.medical_equipment.repositories.TermsRepository;
import com.isa.medical_equipment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api/profiles")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final PenaltyRepository penaltyRepository;
    private final LoyaltyProgramRepository loyaltyProgramRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable long id) {
        var userOpt =userRepository.findById(id);
        if (userOpt.isEmpty()){
            var dto = new CommonResponseDto();
            dto.setMessage("User profile not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var user = userOpt.get();
        var dto = new ProfileResponseDto();
        BeanUtils.copyProperties(user, dto);

        var penalties = penaltyRepository.findByUser(user);
        dto.setPenalties(penalties.size());

        if (user.getLoyaltyProgram() != null) {
            dto.setLoyaltyProgram(user.getLoyaltyProgram().getName());
        }

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProfile(@PathVariable long id, @RequestBody ProfilePutRequestDto request) {
        var userOpt =userRepository.findById(id);
        if (userOpt.isEmpty()){
            var dto = new CommonResponseDto();
            dto.setMessage("User profile not found");
            return ResponseEntity.badRequest().body(dto);
        }

        var user = userOpt.get();
        BeanUtils.copyProperties(request, user);
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/penalties")
    public ResponseEntity<?> getPenalties(@PathVariable long id) {
        var userOpt =userRepository.findById(id);
        if (userOpt.isEmpty()){
            var dto = new CommonResponseDto();
            dto.setMessage("User profile not found");
            return ResponseEntity.badRequest().body(dto);
        }


        var penalties = penaltyRepository.findByUser(userOpt.get());
        var dtos = penalties.stream().map(x -> new PenaltyResponseDto(x.getId(), x.getTimestamp()) );
        return ResponseEntity.ok(dtos);
    }

}
