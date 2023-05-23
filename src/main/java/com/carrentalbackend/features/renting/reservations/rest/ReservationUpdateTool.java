package com.carrentalbackend.features.renting.reservations.rest;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationUpdateTool implements UpdateTool <Reservation, ReservationRequest> {
    @Override
    public void updateEntity(Reservation entity, ReservationRequest updateRequest) {
        //TODO: implement
    }
}
