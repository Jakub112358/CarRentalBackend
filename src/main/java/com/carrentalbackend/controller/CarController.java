package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.CarDto;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.service.CarService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.CAR;

@RestController
@RequestMapping(CAR)
@CrossOrigin(origins = "http://localhost:4200")
public class CarController extends CrudController<Car, CarDto> {
    private final CarService carService;

    public CarController(CarService service) {
        super(service);
        this.carService = service;
    }
}
