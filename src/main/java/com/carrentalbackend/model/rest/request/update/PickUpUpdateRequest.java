package com.carrentalbackend.model.rest.request.update;

import com.carrentalbackend.model.enumeration.RentalActionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickUpUpdateRequest implements UpdateRequest{
    private String comments;
    private LocalDate pickUpDate;
    private LocalDate plannedPickUpDate;
    private RentalActionStatus status;
    private Long employeeId;
    private Long reservationId;
    private Long carId;
    private Long branchOfficeId;
}
