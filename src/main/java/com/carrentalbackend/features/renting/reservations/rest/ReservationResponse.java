package com.carrentalbackend.features.renting.reservations.rest;

import com.carrentalbackend.features.cars.rest.CarResponse;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.offices.rest.OfficeResponse;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse implements Response {
    private long id;
    private LocalDateTime reservationDate;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BigDecimal price;
    private ReservationStatus status;
    private Long clientId;
    private CarResponse car;
    private OfficeResponse pickUpOffice;
    private OfficeResponse returnOffice;
}
