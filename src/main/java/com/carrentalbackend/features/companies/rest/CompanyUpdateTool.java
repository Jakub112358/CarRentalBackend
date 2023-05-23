package com.carrentalbackend.features.companies.rest;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyUpdateTool implements UpdateTool<Company, CompanyRequest> {
    @Override
    public void updateEntity(Company entity, CompanyRequest request) {
        entity.setName(request.getName());
        entity.setDomain(request.getDomain());
        entity.setLogotype(request.getLogotype());
        entity.setAddress(request.getAddress());
        entity.setDifferentOfficesExtraCharge(request.getDifferentOfficesExtraCharge());
    }
}
