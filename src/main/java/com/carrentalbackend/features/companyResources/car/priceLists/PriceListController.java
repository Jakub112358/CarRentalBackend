package com.carrentalbackend.features.companyResources.car.priceLists;

import com.carrentalbackend.features.generics.CrudController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.config.ApiConstraints.ORIGIN;
import static com.carrentalbackend.config.ApiConstraints.PRICE_LIST;

@RestController
@RequestMapping(PRICE_LIST)
@CrossOrigin(origins = ORIGIN)
public class PriceListController extends CrudController<PriceListCreateUpdateRequest, PriceListCreateUpdateRequest> {
    public PriceListController(PriceListService service) {
        super(service);
    }
}
