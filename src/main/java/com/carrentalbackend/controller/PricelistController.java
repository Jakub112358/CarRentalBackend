package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.PricelistDto;
import com.carrentalbackend.model.entity.Pricelist;
import com.carrentalbackend.service.PricelistService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.PRICELIST;

@RestController
@RequestMapping(PRICELIST)
@CrossOrigin(origins = "http://localhost:4200")
public class PricelistController extends CrudController<Pricelist, PricelistDto> {
    public PricelistController(PricelistService service) {
        super(service);
    }
}
