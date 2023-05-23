package com.carrentalbackend.features.companyResources.offices;

import com.carrentalbackend.features.companyResources.offices.rest.OfficeRequest;
import com.carrentalbackend.features.generics.CrudController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.config.ApiConstraints.OFFICE;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(OFFICE)
@CrossOrigin(origins = ORIGIN)

public class OfficeController extends CrudController<OfficeRequest> {

    public OfficeController(OfficeService service) {
        super(service);

    }
}
