package com.carrentalbackend.model.rest.request.create;

import com.carrentalbackend.model.enumeration.RentalActionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickUpCreateRequest implements CreateRequest{
    private String comments;
    private LocalDate pickUpDate;
    private LocalDate plannedPickUpDate;
    private RentalActionStatus status;
    private Long employeeId;
    private Long reservationId;
    private Long carId;
    private Long officeId;
}
