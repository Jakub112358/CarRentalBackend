package com.carrentalbackend.features.renting;

import com.carrentalbackend.exception.InvalidReservationDataException;

import java.time.LocalDate;

public class RentingValidation {
    public void validateRentingDates(LocalDate dateFrom, LocalDate dateTo) {
        if(dateFrom.isAfter(dateTo))
            throw new InvalidReservationDataException("incorrect reservation dates");
    }
}
