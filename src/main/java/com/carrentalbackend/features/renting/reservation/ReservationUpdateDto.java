package com.carrentalbackend.features.renting.reservation;

import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationUpdateDto implements UpdateDto {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Client client;
    private Office pickUpOffice;
    private Office returnOffice;
    private ReservationStatus status;
}
