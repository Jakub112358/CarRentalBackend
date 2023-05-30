package com.carrentalbackend.features.renting.reservations.rest;

import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.validation.existingCarId.ExistingCarId;
import com.carrentalbackend.validation.existingClientId.ExistingClientId;
import com.carrentalbackend.validation.existingOfficeId.ExistingOfficeId;
import jakarta.validation.constraints.FutureOrPresent;
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
public class ReservationCreateRequest implements CreateRequest {
    @NotNull
    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateFrom;
    @NotNull
    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateTo;
    @NotNull
    private BigDecimal price;
    @NotNull
    @ExistingClientId
    private Long clientId;
    @NotNull
    @ExistingCarId
    private Long carId;
    @NotNull
    @ExistingOfficeId
    private Long pickUpOfficeId;
    @NotNull
    @ExistingOfficeId
    private Long returnOfficeId;
}
