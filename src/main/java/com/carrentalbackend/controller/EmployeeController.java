package com.carrentalbackend.controller;

import com.carrentalbackend.model.rest.request.create.EmployeeCreateRequest;
import com.carrentalbackend.model.rest.request.update.EmployeeUpdateRequest;
import com.carrentalbackend.model.rest.response.EmployeeResponse;
import com.carrentalbackend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.controller.ApiConstraints.EMPLOYEE;


@RestController
@RequestMapping(EMPLOYEE)
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController extends CrudController<EmployeeCreateRequest, EmployeeUpdateRequest> {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        super(service);
        this.service = service;
    }

    @GetMapping(params = "officeId")
    public ResponseEntity<Set<EmployeeResponse>> findByOfficeId(@RequestParam Long officeId) {
        return ResponseEntity.ok(service.findAllByOfficeId(officeId));
    }

}
