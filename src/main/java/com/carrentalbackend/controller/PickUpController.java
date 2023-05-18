package com.carrentalbackend.controller;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.model.rest.request.create.PickUpCreateRequest;
import com.carrentalbackend.model.rest.request.update.PickUpUpdateRequest;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.service.PickUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.ORIGIN;
import static com.carrentalbackend.config.ApiConstraints.PICK_UP;

@RestController
@CrossOrigin(origins = ORIGIN)
@RequestMapping(PICK_UP)
public class PickUpController extends CrudController<PickUpCreateRequest, PickUpUpdateRequest> {
    private final PickUpService pickUpService;

    public PickUpController(PickUpService service) {
        super(service);
        this.pickUpService = service;
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<Response>> findAllByOfficeId(@RequestParam Long officeId) {
        return ResponseEntity.ok(pickUpService.findAllByOfficeId(officeId));
    }
}
