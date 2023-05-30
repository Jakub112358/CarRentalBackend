package com.carrentalbackend.features.renting.pickUps.rest;

import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.PickUp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PickUpMapper {

    public Response toResponse(PickUp entity) {
        Long employeeId = entity.getEmployee() != null ? entity.getEmployee().getId() : null;
        Long reservationId = entity.getReservation() != null ? entity.getReservation().getId() : null;
        Long carId = entity.getCar() != null ? entity.getCar().getId() : null;
        Long branchOfficeId = entity.getOffice() != null ? entity.getOffice().getId() : null;
        return PickUpResponse.builder()
                .id(entity.getId())
                .comments(entity.getComments())
                .plannedPickUpDate(entity.getPlannedPickUpDate())
                .pickUpDate(entity.getPickUpDate())
                .status(entity.getStatus())
                .employeeId(employeeId)
                .reservationId(reservationId)
                .carId(carId)
                .branchOfficeId(branchOfficeId)
                .build();
    }

}
