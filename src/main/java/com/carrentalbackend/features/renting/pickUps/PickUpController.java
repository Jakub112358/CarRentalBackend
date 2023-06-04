package com.carrentalbackend.features.renting.pickUps;

import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.ORIGIN;
import static com.carrentalbackend.config.ApiConstraints.PICK_UP;

@RestController
@CrossOrigin(origins = ORIGIN)
@RequestMapping(PICK_UP)
@RequiredArgsConstructor
public class PickUpController {
    private final PickUpService pickUpService;

    @GetMapping
    public ResponseEntity<Set<Response>> findAll() {
        return ResponseEntity.ok(pickUpService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pickUpService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @Valid @RequestBody PickUpUpdateRequest updateRequest) {
        return ResponseEntity.ok(pickUpService.update(id, updateRequest));
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<Response>> findAllByOfficeId(@RequestParam Long officeId) {
        return ResponseEntity.ok(pickUpService.findAllByOfficeId(officeId));
    }
}
