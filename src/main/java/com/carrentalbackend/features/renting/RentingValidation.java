package com.carrentalbackend.features.renting;

import com.carrentalbackend.exception.InvalidReservationDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RentingValidation {
    public void throwIfInvalidRentingDatesOrder(LocalDate dateFrom, LocalDate dateTo) {
        if(dateFrom.isAfter(dateTo))
            throw new InvalidReservationDataException("incorrect reservation dates");
    }
}
