package com.carrentalbackend.features.renting.pickUps;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.PickUp;
import org.springframework.stereotype.Component;

@Component
public class PickUpUpdateTool implements UpdateTool <PickUp, PickUpCreateUpdateRequest> {
    @Override
    public void updateEntity(PickUp entity, PickUpCreateUpdateRequest updateRequest) {

    }
}
