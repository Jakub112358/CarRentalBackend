package com.carrentalbackend.features.renting.reservation;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationUpdateTool implements UpdateTool <Reservation, ReservationCreateUpdateRequest> {
    @Override
    public void updateEntity(Reservation entity, ReservationCreateUpdateRequest updateRequest) {
        //TODO: implement
    }
}
