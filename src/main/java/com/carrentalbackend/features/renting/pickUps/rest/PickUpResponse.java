package com.carrentalbackend.features.renting.pickUps.rest;

import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickUpResponse implements Response {
    private long id;
    private String comments;
    private LocalDate pickUpDate;
    private LocalDate plannedPickUpDate;
    private RentalActionStatus status;
    private Long employeeId;
    private Long reservationId;
    private Long carId;
    private Long branchOfficeId;
}
