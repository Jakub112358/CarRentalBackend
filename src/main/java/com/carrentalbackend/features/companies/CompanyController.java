package com.carrentalbackend.features.companies;

import com.carrentalbackend.features.companies.rest.CompanyRequest;
import com.carrentalbackend.features.generics.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.carrentalbackend.config.ApiConstraints.COMPANY;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(COMPANY)
@CrossOrigin(origins = ORIGIN)
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<Response> findAll() {
        return ResponseEntity.ok(companyService.findCompany());
    }

    @PatchMapping
    public ResponseEntity<Response> update(@Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.ok(companyService.update(request));
    }

}
