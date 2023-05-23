package com.carrentalbackend.features.companyResources.office;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Office;
import org.springframework.stereotype.Component;

@Component
public class OfficeUpdateTool implements UpdateTool<Office, OfficeCreateUpdateRequest> {
    @Override
    public void updateEntity(Office entity, OfficeCreateUpdateRequest updateRequest) {
        //TODO: implement
    }
}
