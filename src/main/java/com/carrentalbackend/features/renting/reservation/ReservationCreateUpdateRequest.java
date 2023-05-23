package com.carrentalbackend.features.renting.reservation;

import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.features.generics.UpdateRequest;
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
public class ReservationCreateUpdateRequest implements CreateRequest, UpdateRequest {
    private LocalDateTime reservationDate;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BigDecimal price;
    private ReservationStatus status;
    private Long clientId;
    private Long carId;
    private Long pickUpOfficeId;
    private Long returnOfficeId;
}
