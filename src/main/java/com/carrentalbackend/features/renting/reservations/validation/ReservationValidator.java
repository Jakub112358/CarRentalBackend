package com.carrentalbackend.features.renting.reservations.validation;

import com.carrentalbackend.exception.InvalidReservationDataException;
import com.carrentalbackend.features.generics.Request;
import org.springframework.stereotype.Component;

@Component
public class ReservationValidator {
    public void validate(Request request) {
        validatePrice(request);
        validateDates(request);
    }

    //TODO: implement
    private void validateDates(Request request) {
        if (false)
            throw new InvalidReservationDataException("incorrect reservation dates");
    }

    //TODO: implement
    private void validatePrice(Request request) {
        if (false)
            throw new InvalidReservationDataException("incorrect reservation price");
    }
}
