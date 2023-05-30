package com.carrentalbackend.features.renting.carReturns;

import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.carReturns.rest.CarReturnUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.CAR_RETURN;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@CrossOrigin(origins = ORIGIN)
@RequestMapping(CAR_RETURN)
@RequiredArgsConstructor
public class CarReturnController {
    private final CarReturnService carReturnService;

    @GetMapping
    public ResponseEntity<Set<Response>> findAll() {
        return ResponseEntity.ok(carReturnService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(carReturnService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @Valid @RequestBody CarReturnUpdateRequest updateRequest) {
        return ResponseEntity.ok(carReturnService.update(id, updateRequest));
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<Response>> findAllByOfficeId(@RequestParam Long officeId) {
        return ResponseEntity.ok(carReturnService.findAllByOfficeId(officeId));
    }

}
