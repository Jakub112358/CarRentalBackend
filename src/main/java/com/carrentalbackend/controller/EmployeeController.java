package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.EmployeeDto;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.service.EmployeeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.EMPLOYEE;


@RestController
@RequestMapping(EMPLOYEE)
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController extends CrudController<Employee, EmployeeDto> {
    public EmployeeController(EmployeeService service) {
        super(service);
    }
}
