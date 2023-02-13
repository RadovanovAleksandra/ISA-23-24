package com.isa.medical_equipment.security;

import com.isa.medical_equipment.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        var optUser = userRepository.findByEmail(username);
        if (optUser.isPresent()) {
            return UserDetailsImpl.build(optUser.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
