package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.CarDto;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.request.CarSearchRequest;
import com.carrentalbackend.service.CarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.carrentalbackend.controller.ApiConstraints.CAR;

@RestController
@RequestMapping(CAR)
@CrossOrigin(origins = "http://localhost:4200")
public class CarController extends CrudController<Car, CarDto> {
    private final CarService service;

    public CarController(CarService service) {
        super(service);
        this.service = service;
    }

    @GetMapping(params = "branchOfficeId")
    public ResponseEntity<List<CarDto>> findByBranchOfficeId(@RequestParam Long branchOfficeId) {
        return ResponseEntity.ok(service.findAllByBranchOfficeId(branchOfficeId));
    }

    //    @PostMapping(params = {"dateFrom","dateTo","picUpOfficeId"})
    //TODO consider if this endpoint is correct
    @PostMapping("/search")
    public ResponseEntity<List<CarDto>> findByAvailableInDatesAndCriteria(@RequestBody(required = false) CarSearchRequest criteria,
                                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                                          @RequestParam Long pickUpOfficeId) {
        return ResponseEntity.ok(service.findByAvailableInDatesAndCriteria(dateFrom, dateTo, pickUpOfficeId, criteria));

    }

}
