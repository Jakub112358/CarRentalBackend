package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.PickUpDto;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.model.rest.request.create.OfficeCreateRequest;
import com.carrentalbackend.model.rest.request.create.PickUpCreateRequest;
import com.carrentalbackend.model.rest.request.update.PickUpUpdateRequest;
import com.carrentalbackend.service.PickUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.carrentalbackend.controller.ApiConstraints.PICK_UP;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(PICK_UP)
public class PickUpController extends CrudController<PickUpCreateRequest, PickUpUpdateRequest> {
    private final PickUpService pickUpService;
    public PickUpController(PickUpService service) {
        super(service);
        this.pickUpService = service;
    }

    @GetMapping (params = "officeId")
    public ResponseEntity<Set<PickUpDto>> findAllByOfficeId(@RequestParam Long officeId){
        return ResponseEntity.ok(pickUpService.findAllByOfficeId(officeId));
    }
}
