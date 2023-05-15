package com.carrentalbackend.controller;

import com.carrentalbackend.model.rest.request.create.CompanyCreateRequest;
import com.carrentalbackend.model.rest.request.update.CompanyUpdateRequest;
import com.carrentalbackend.service.CompanyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.COMPANY;

@RestController
@RequestMapping(COMPANY)
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController extends CrudController<CompanyCreateRequest, CompanyUpdateRequest> {

    public CompanyController(CompanyService service) {
        super(service);
    }


}
