package com.carrentalbackend.features.renting;

import com.carrentalbackend.exception.InvalidReservationDataException;
import com.carrentalbackend.features.renting.reservations.rest.ReservationCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RentingValidation {

    public void throwIfInvalidRentingDatesOrder(LocalDate dateFrom, LocalDate dateTo) {
        if(dateFrom.isAfter(dateTo))
            throw new InvalidReservationDataException("incorrect reservation dates");
    }

    public void throwIfInvalidPrice(BigDecimal expectedPrice, ReservationCreateRequest request) {
        var requestPrice = request.getPrice();

        if(!expectedPrice.equals(requestPrice))
            throw new InvalidReservationDataException("incorrect given price, given: " + requestPrice + " , should be: " + expectedPrice);
    }
}
