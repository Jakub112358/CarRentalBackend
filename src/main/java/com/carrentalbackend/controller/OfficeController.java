package com.carrentalbackend.controller;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.companyResources.office.OfficeCreateRequest;
import com.carrentalbackend.features.companyResources.office.OfficeUpdateRequest;
import com.carrentalbackend.features.companyResources.office.OfficeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.config.ApiConstraints.OFFICE;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(OFFICE)
@CrossOrigin(origins = ORIGIN)

public class OfficeController extends CrudController<OfficeCreateRequest, OfficeUpdateRequest> {

    public OfficeController(OfficeService service) {
        super(service);

    }
}
