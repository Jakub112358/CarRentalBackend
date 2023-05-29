package com.carrentalbackend.features.renting.reservations.rest;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationUpdateTool implements UpdateTool <Reservation, ReservationCreateRequest> {
    @Override
    public void updateEntity(Reservation entity, ReservationCreateRequest updateRequest) {
        //TODO: implement
    }
}
