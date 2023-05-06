package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.OfficeDto;
import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.service.OfficeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.OFFICE;

@RestController
@RequestMapping(OFFICE)
@CrossOrigin(origins = "http://localhost:4200")

public class OfficeController extends CrudController<BranchOffice, OfficeDto> {
    private final OfficeService officeService;
    public OfficeController(OfficeService service) {
        super(service);
        this.officeService = service;
    }
}
