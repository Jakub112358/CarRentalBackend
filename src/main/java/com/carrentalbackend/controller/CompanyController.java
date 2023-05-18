package com.carrentalbackend.controller;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.companyResources.company.CompanyCreateRequest;
import com.carrentalbackend.features.companyResources.company.CompanyUpdateRequest;
import com.carrentalbackend.features.companyResources.company.CompanyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.config.ApiConstraints.COMPANY;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(COMPANY)
@CrossOrigin(origins = ORIGIN)
public class CompanyController extends CrudController<CompanyCreateRequest, CompanyUpdateRequest> {

    public CompanyController(CompanyService service) {
        super(service);
    }


}
