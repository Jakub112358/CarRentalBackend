package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.OfficeDto;
import com.carrentalbackend.service.OfficeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.OFFICE;

@RestController
@RequestMapping(OFFICE)

public class OfficeController extends CrudController<OfficeDto> {
    private final OfficeService officeService;
    public OfficeController(OfficeService service) {
        super(service);
        this.officeService = service;
    }
}
