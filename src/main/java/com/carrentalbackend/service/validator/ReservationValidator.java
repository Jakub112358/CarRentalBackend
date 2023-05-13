package com.carrentalbackend.service.validator;

import com.carrentalbackend.exception.InvalidReservationDataException;
import com.carrentalbackend.model.dto.crudDto.ReservationDto;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import org.springframework.stereotype.Component;

@Component
public class ReservationValidator {
    public void validate(CreateRequest request) {
        validatePrice(request);
        validateDates(request);
    }

    //TODO: implement
    private void validateDates(CreateRequest request) {
        if (false)
            throw new InvalidReservationDataException("incorrect reservation dates");
    }

    //TODO: implement
    private void validatePrice(CreateRequest request) {
        if (false)
            throw new InvalidReservationDataException("incorrect reservation price");
    }
}
