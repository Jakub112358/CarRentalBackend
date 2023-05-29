package com.carrentalbackend.features.cars;

import com.carrentalbackend.features.cars.rest.CarRequest;
import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.generics.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.CAR;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(CAR)
@CrossOrigin(origins = ORIGIN)
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
}
