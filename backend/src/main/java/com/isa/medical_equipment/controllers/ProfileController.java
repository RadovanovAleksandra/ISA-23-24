package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.ProfileResponseDto;
import com.isa.medical_equipment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
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
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProfile(@PathVariable long id, @RequestBody ProfileResponseDto request) {
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

}
