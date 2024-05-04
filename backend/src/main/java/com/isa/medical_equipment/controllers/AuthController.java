package com.isa.medical_equipment.controllers;

import com.isa.medical_equipment.dto.CommonResponseDto;
import com.isa.medical_equipment.dto.LoginRequestDto;
import com.isa.medical_equipment.dto.SignUpRequestDto;
import com.isa.medical_equipment.exceptions.ErrorType;
import com.isa.medical_equipment.services.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Controller for managing authentication")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "User Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in"),
            @ApiResponse(responseCode = "400", description = "Login failed")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestBody) {
        try {
            return ResponseEntity.ok(authService.login(requestBody));
        } catch (Exception ex) {
            var dto = new CommonResponseDto();
            dto.setMessage("Login failed");
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @Operation(summary = "User singing up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully signed up"),
            @ApiResponse(responseCode = "400", description = "Sign up failed")
    })
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@RequestBody SignUpRequestDto requestBody,
                                               HttpServletRequest request) {

        var error = authService.signup(requestBody, request);

        var dto = new CommonResponseDto();
        if (error == ErrorType.USERNAME_EXISTS ) {
            dto.setMessage("Username already exists");
            return ResponseEntity.badRequest().body(dto);
        } else if (error == ErrorType.EMAIL_EXISTS) {
            dto.setMessage("Email already exists");
            return ResponseEntity.badRequest().body(dto);
        }

        dto.setMessage("Successfully signed up");
        return ResponseEntity.ok(dto);
    }

}
