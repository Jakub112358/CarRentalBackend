package com.carrentalbackend.features.authentication;

import com.carrentalbackend.config.security.JwtService;
import com.carrentalbackend.features.authentication.rest.AuthenticationRequest;
import com.carrentalbackend.features.authentication.rest.AuthenticationResponse;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authenticationToken);
        String jwtToken = generateToken(request);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private String generateToken(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", user.getRole());
        extraClaims.put("id", user.getId());
        return jwtService.generateToken(user, extraClaims);
    }
}
