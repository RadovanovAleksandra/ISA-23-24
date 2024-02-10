package com.isa.medical_equipment.services.implementation;

import com.isa.medical_equipment.dto.LoginRequestDto;
import com.isa.medical_equipment.dto.LoginResponseDto;
import com.isa.medical_equipment.dto.SignUpRequestDto;
import com.isa.medical_equipment.entity.RoleEnum;
import com.isa.medical_equipment.entity.User;
import com.isa.medical_equipment.exceptions.ErrorType;
import com.isa.medical_equipment.repositories.UserRepository;
import com.isa.medical_equipment.security.JwtUtils;
import com.isa.medical_equipment.security.UserDetailsImpl;
import com.isa.medical_equipment.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public LoginResponseDto login(LoginRequestDto requestBody) throws Exception {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestBody.getUsername(),
                        requestBody.getUsername()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var user = userRepository.findById(userDetails.getId());
        if (!user.get().isVerified()) {
            throw new Exception("Login failed");
        }

        String jwt = jwtUtils.generateJwtToken(authentication);
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        return new LoginResponseDto(
                jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role);
    }


    public ErrorType signup(SignUpRequestDto requestBody,
                                  HttpServletRequest request) {

        if (userRepository.existsByEmail(requestBody.getEmail())) {
            return ErrorType.EMAIL_EXISTS;
        }

        var user = new User();
        user.setEmail(requestBody.getEmail());
        user.setPassword(encoder.encode(requestBody.getPassword()));
        user.setName(requestBody.getName());
        user.setLastName(requestBody.getLastName());
        user.setCity(requestBody.getCity());
        user.setPhone(requestBody.getPhone());
        user.setProfession(requestBody.getProfession());
        user.setState(requestBody.getState());
        user.setRole(RoleEnum.USER);
        userRepository.save(user);

        return null;
    }
}
