package com.isa.medical_equipment.services.interfaces;

import com.isa.medical_equipment.dto.LoginRequestDto;
import com.isa.medical_equipment.dto.LoginResponseDto;
import com.isa.medical_equipment.dto.SignUpRequestDto;
import com.isa.medical_equipment.exceptions.ErrorType;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    ErrorType signup(SignUpRequestDto requestBody, HttpServletRequest request);
    LoginResponseDto login(LoginRequestDto requestBody)  throws Exception;
}
