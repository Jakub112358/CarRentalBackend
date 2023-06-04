package com.carrentalbackend.features.renting.pickUps.rest;

import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.validation.existingCarId.ExistingCarId;
import com.carrentalbackend.validation.existingEmployeeId.ExistingEmployeeId;
import com.carrentalbackend.validation.existingOfficeId.ExistingOfficeId;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickUpUpdateRequest implements UpdateRequest {
    private String comments;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate pickUpDate;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate plannedPickUpDate;
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
