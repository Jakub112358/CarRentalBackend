package com.carrentalbackend.controller;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.model.rest.request.create.OfficeCreateRequest;
import com.carrentalbackend.model.rest.request.update.OfficeUpdateRequest;
import com.carrentalbackend.service.OfficeService;
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
