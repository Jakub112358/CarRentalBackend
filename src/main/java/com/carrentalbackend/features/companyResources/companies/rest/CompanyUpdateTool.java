package com.carrentalbackend.features.companyResources.companies.rest;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyUpdateTool implements UpdateTool <Company, CompanyRequest> {
    @Override
    public void updateEntity(Company entity, CompanyRequest updateRequest) {
        //TODO: implement
    }
}
