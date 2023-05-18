package com.carrentalbackend.controller;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.renting.carReturns.CarReturnCreateRequest;
import com.carrentalbackend.features.renting.carReturns.CarReturnUpdateRequest;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.carReturns.CarReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.CAR_RETURN;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@CrossOrigin(origins = ORIGIN)
@RequestMapping(CAR_RETURN)
public class CarReturnController extends CrudController<CarReturnCreateRequest, CarReturnUpdateRequest> {
    private final CarReturnService carReturnService;

    public CarReturnController(CarReturnService service) {
        super(service);
        this.carReturnService = service;
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<Response>> findAllByOfficeId(@RequestParam Long officeId) {
        return ResponseEntity.ok(carReturnService.findAllByOfficeId(officeId));
    }

}
