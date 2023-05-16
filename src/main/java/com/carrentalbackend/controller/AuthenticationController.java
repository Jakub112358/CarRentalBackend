package com.carrentalbackend.controller;

import com.carrentalbackend.model.rest.request.auth.AuthenticationRequest;
import com.carrentalbackend.model.rest.request.auth.RegisterRequest;
import com.carrentalbackend.model.rest.response.auth.AuthenticationResponse;
import com.carrentalbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.carrentalbackend.controller.ApiConstraints.AUTHENTICATION;

@RestController
@RequestMapping(AUTHENTICATION)
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }



    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
