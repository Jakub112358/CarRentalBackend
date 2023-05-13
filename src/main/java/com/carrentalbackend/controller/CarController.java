package com.carrentalbackend.controller;

import com.carrentalbackend.model.rest.request.CarSearchByCriteriaRequest;
import com.carrentalbackend.model.rest.request.create.CarCreateRequest;
import com.carrentalbackend.model.rest.request.update.CarUpdateRequest;
import com.carrentalbackend.model.rest.response.CarRentResponse;
import com.carrentalbackend.model.rest.response.CarResponse;
import com.carrentalbackend.service.CarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

import static com.carrentalbackend.controller.ApiConstraints.CAR;

@RestController
@RequestMapping(CAR)
@CrossOrigin(origins = "http://localhost:4200")
public class CarController extends CrudController<CarCreateRequest, CarUpdateRequest> {
    private final CarService service;

    public CarController(CarService service) {
        super(service);
        this.service = service;
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<CarResponse>> findByOfficeId(@RequestParam Long officeId) {
        return ResponseEntity.ok(service.findAllByOfficeId(officeId));
    }

    //TODO: consider if this endpoint is correct
    @PostMapping("/search")
    public ResponseEntity<Set<CarRentResponse>> findByAvailableInDatesAndCriteria(@RequestBody(required = false) CarSearchByCriteriaRequest criteria,
                                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                                                  @RequestParam Long pickUpOfficeId,
                                                                                  @RequestParam Long returnOfficeId) {
        return ResponseEntity.ok(service.findByAvailableInDatesAndCriteria(dateFrom, dateTo, pickUpOfficeId, returnOfficeId, criteria));

    }

}
