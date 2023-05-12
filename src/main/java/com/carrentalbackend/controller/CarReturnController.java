package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.CarReturnDto;
import com.carrentalbackend.model.entity.CarReturn;
import com.carrentalbackend.service.CarReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.controller.ApiConstraints.CAR_RETURN;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(CAR_RETURN)
public class CarReturnController extends CrudController<CarReturn, CarReturnDto> {
    private final CarReturnService carReturnService;

    public CarReturnController(CarReturnService service) {
        super(service);
        this.carReturnService = service;
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<CarReturnDto>> findAllByOfficeId(@RequestParam Long officeId) {
        return ResponseEntity.ok(carReturnService.findAllByOfficeId(officeId));
    }

}
