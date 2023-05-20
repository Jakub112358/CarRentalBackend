package com.carrentalbackend.features.renting.carReturns;

import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.features.generics.UpdateRequest;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarReturnUpdateRequest implements UpdateRequest {
    private String comments;
    private BigDecimal extraCharge;
    private LocalDate returnDate;
    private LocalDate plannedReturnDate;
    private RentalActionStatus status;
    private Long employeeId;
    private Long reservationId;
    private Long carId;
    private Long branchOfficeId;
    private int mileage;
}