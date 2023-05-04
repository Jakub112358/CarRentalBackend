package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.EmployeeDto;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.carrentalbackend.controller.ApiConstraints.EMPLOYEE;


@RestController
@RequestMapping(EMPLOYEE)
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController extends CrudController<Employee, EmployeeDto> {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) {
        super(service);
        this.service = service;
    }

    @GetMapping(params = "branchOfficeId")
    public ResponseEntity<List<EmployeeDto>> findByBranchOfficeId(@RequestParam Long branchOfficeId) {
        return ResponseEntity.ok(service.findAllByBranchOfficeId(branchOfficeId));
    }

}
