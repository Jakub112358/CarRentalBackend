package com.carrentalbackend.features.companyResources.company;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyUpdateTool implements UpdateTool <Company, CompanyCreateUpdateRequest> {
    @Override
    public void updateEntity(Company entity, CompanyCreateUpdateRequest updateRequest) {
        //TODO: implement
    }
}
