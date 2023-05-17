package com.carrentalbackend.service;

import com.carrentalbackend.config.JwtService;
import com.carrentalbackend.model.entity.User;
import com.carrentalbackend.model.rest.request.auth.AuthenticationRequest;
import com.carrentalbackend.model.rest.response.auth.AuthenticationResponse;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //TODO: think about this exception
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", user.getRole());
        String jwtToken = jwtService.generateToken(user, extraClaims);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
