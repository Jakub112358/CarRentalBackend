package com.carrentalbackend.features.companyResources.employee;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.generics.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.EMPLOYEE;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;


@RestController
@RequestMapping(EMPLOYEE)
@CrossOrigin(origins = ORIGIN)
public class EmployeeController extends CrudController<EmployeeRequest> {
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
