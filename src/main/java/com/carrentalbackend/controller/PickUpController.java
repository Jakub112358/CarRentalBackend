package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.PickUpDto;
import com.carrentalbackend.model.entity.CarPickUp;
import com.carrentalbackend.service.PickUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.carrentalbackend.controller.ApiConstraints.PICK_UP;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(PICK_UP)
public class PickUpController extends CrudController<CarPickUp, PickUpDto> {
    private final PickUpService pickUpService;
    public PickUpController(PickUpService service) {
        super(service);
        this.pickUpService = service;
    }

    @GetMapping (params = "officeId")
    public ResponseEntity<List<PickUpDto>> findAllByOfficeId(@RequestParam Long officeId){
        return ResponseEntity.ok(pickUpService.findAllByOfficeId(officeId));
    }
}
