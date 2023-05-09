package com.carrentalbackend.service.validator;

import com.carrentalbackend.exception.InvalidReservationDataException;
import com.carrentalbackend.model.dto.crudDto.ReservationDto;
import org.springframework.stereotype.Component;

@Component
public class ReservationValidator {
    public void validate(ReservationDto requestDto) {
        validatePrice(requestDto);
        validateDates(requestDto);
    }

    //TODO: implement
    private void validateDates(ReservationDto requestDto) {
        if (false)
            throw new InvalidReservationDataException("incorrect reservation dates");
    }

    //TODO: implement
    private void validatePrice(ReservationDto requestDto) {
        if (false)
            throw new InvalidReservationDataException("incorrect reservation price");
    }
}
