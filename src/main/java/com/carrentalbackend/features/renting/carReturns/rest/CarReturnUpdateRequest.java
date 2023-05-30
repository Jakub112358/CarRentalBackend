package com.carrentalbackend.features.renting.carReturns.rest;

import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.validation.existingCarId.ExistingCarId;
import com.carrentalbackend.validation.existingEmployeeId.ExistingEmployeeId;
import com.carrentalbackend.validation.existingOfficeId.ExistingOfficeId;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate plannedReturnDate;
    @NotNull
    private RentalActionStatus status;
    @NotNull
    @ExistingEmployeeId
    private Long employeeId;
    @NotNull
    @ExistingCarId
    private Long carId;
    @NotNull
    @ExistingOfficeId
    private Long officeId;
}
