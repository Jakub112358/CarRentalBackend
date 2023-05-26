package com.carrentalbackend.features.cars;

import com.carrentalbackend.features.cars.rest.CarRequest;
import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.CarRentResponse;
import com.carrentalbackend.features.renting.CarSearchByCriteriaRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.CAR;

@RestController
@RequestMapping(CAR)
//@CrossOrigin(origins = ORIGIN)
@CrossOrigin
public class CarController extends CrudController<CarRequest, CarRequest> {
    private final CarService service;

    public CarController(CarService service) {
        super(service);
        this.service = service;
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<Response>> findByOfficeId(@RequestParam Long officeId) {
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
