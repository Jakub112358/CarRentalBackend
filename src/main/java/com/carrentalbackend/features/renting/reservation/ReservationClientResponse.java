package com.carrentalbackend.features.renting.reservation;

import com.carrentalbackend.features.companyResources.car.CarResponse;
import com.carrentalbackend.features.companyResources.office.OfficeResponse;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationClientResponse {
    private long id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BigDecimal price;
    private ReservationStatus status;
    private Long clientId;
    private CarResponse car;
    private OfficeResponse pickUpOffice;
    private OfficeResponse returnOffice;
}