package com.carrentalbackend.controller;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.model.rest.request.create.EmployeeCreateRequest;
import com.carrentalbackend.model.rest.request.update.EmployeeUpdateRequest;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.EMPLOYEE;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;


@RestController
@RequestMapping(EMPLOYEE)
@CrossOrigin(origins = ORIGIN)
public class EmployeeController extends CrudController<EmployeeCreateRequest, EmployeeUpdateRequest> {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        super(service);
        this.service = service;
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<Response>> findByOfficeId(@RequestParam Long officeId) {
        return ResponseEntity.ok(service.findAllByOfficeId(officeId));
    }

}
