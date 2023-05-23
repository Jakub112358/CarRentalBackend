package com.carrentalbackend.features.renting.carReturns;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.CarReturn;
import org.springframework.stereotype.Component;

@Component
public class CarReturnUpdateTool implements UpdateTool <CarReturn, CarReturnRequest> {
    @Override
    public void updateEntity(CarReturn entity, CarReturnRequest updateRequest) {
        //TODO: implement
    }
}
