package com.carrentalbackend.features.renting.pickUps.rest;

import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickUpRequest implements CreateRequest, UpdateRequest {
    private String comments;
    private LocalDate pickUpDate;
    private LocalDate plannedPickUpDate;
    private RentalActionStatus status;
    private Long employeeId;
    private Long reservationId;
    private Long carId;
    private Long officeId;
}
