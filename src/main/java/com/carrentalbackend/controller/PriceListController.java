package com.carrentalbackend.controller;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.model.rest.request.create.PriceListCreateRequest;
import com.carrentalbackend.model.rest.request.update.PriceListUpdateRequest;
import com.carrentalbackend.service.PriceListService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.config.ApiConstraints.ORIGIN;
import static com.carrentalbackend.config.ApiConstraints.PRICE_LIST;

@RestController
@RequestMapping(PRICE_LIST)
@CrossOrigin(origins = ORIGIN)
public class PriceListController extends CrudController<PriceListCreateRequest, PriceListUpdateRequest> {
    public PriceListController(PriceListService service) {
        super(service);
    }
}
