package com.carrentalbackend.features.renting.reservations.rest;

import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationUpdateRequest implements UpdateRequest {
    @NotNull
    private ReservationStatus reservationStatus;

}
