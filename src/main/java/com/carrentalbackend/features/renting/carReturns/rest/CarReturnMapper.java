package com.carrentalbackend.features.renting.carReturns.rest;

import com.carrentalbackend.model.entity.CarReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarReturnMapper {

    public CarReturnResponse toResponse(CarReturn entity) {
        Long employeeId = entity.getEmployee() != null ? entity.getEmployee().getId() : null;
        Long reservationId = entity.getReservation() != null ? entity.getReservation().getId() : null;
        Long carId = entity.getCar() != null ? entity.getCar().getId() : null;
        Long branchOfficeId = entity.getOffice() != null ? entity.getOffice().getId() : null;
        return CarReturnResponse.builder()
                .id(entity.getId())
                .comments(entity.getComments())
                .extraCharge(entity.getExtraCharge())
                .returnDate(entity.getReturnDate())
                .plannedReturnDate(entity.getPlannedReturnDate())
                .status(entity.getStatus())
                .employeeId(employeeId)
                .reservationId(reservationId)
                .carId(carId)
                .officeId(branchOfficeId)
                .build();
    }
}
