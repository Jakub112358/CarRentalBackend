package com.carrentalbackend.features.companyResources.company;

import com.carrentalbackend.features.generics.CrudController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.config.ApiConstraints.COMPANY;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(COMPANY)
@CrossOrigin(origins = ORIGIN)
public class CompanyController extends CrudController<CompanyCreateUpdateRequest, CompanyCreateUpdateRequest> {

    public CompanyController(CompanyService service) {
        super(service);
    }


}
