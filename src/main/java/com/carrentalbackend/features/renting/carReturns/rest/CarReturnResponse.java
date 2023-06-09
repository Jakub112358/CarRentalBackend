package com.carrentalbackend.features.renting.carReturns.rest;

import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarReturnResponse implements Response {
    private long id;
    private String comments;
    private BigDecimal extraCharge;
    private LocalDate returnDate;
    private LocalDate plannedReturnDate;
    private RentalActionStatus status;
    private Long employeeId;
    private Long reservationId;
    private Long carId;
    private Long officeId;
}
