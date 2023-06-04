package com.carrentalbackend.features.offices.rest;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Office;
import org.springframework.stereotype.Component;

@Component
public class OfficeUpdateTool implements UpdateTool<Office, OfficeRequest> {
    @Override
    public void updateEntity(Office entity, OfficeRequest updateRequest) {
        entity.setAddress(updateRequest.getAddress());
    }
}
