package com.carrentalbackend.features.authentication;

import com.carrentalbackend.model.rest.request.auth.AuthenticationRequest;
import com.carrentalbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.carrentalbackend.config.ApiConstraints.AUTHENTICATION;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(AUTHENTICATION)
@CrossOrigin(origins = ORIGIN)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;



    @PostMapping()
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
