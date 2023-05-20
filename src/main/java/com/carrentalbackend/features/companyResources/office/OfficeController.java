package com.carrentalbackend.features.companyResources.office;

import com.carrentalbackend.features.generics.CrudController;
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
