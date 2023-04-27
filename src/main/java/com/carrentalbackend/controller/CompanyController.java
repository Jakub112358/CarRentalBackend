package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.carrentalbackend.controller.ApiConstraints.COMPANY;

@RestController
@RequestMapping(COMPANY)
public class CompanyController extends CrudController<Company, CompanyDto> {
    private final CompanyService companyService;

    public CompanyController(CompanyService service) {
        super(service);
        this.companyService = service;
    }


}
