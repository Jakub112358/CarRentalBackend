package com.carrentalbackend.features.authentication;

import com.carrentalbackend.features.authentication.rest.AuthenticationRequest;
import com.carrentalbackend.features.authentication.rest.AuthenticationResponse;
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
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
