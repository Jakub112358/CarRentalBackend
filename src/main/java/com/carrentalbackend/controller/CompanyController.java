package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.service.CompanyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.COMPANY;

@RestController
@RequestMapping(COMPANY)
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController extends CrudController<Company, CompanyDto> {
    private final CompanyService companyService;

    public CompanyController(CompanyService service) {
        super(service);
        this.companyService = service;
    }


}
