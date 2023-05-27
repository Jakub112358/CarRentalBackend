package com.carrentalbackend.features.renting;

import com.carrentalbackend.exception.InvalidReservationDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class RentingValidationTest {
    @InjectMocks
    private RentingValidation rentingValidation;

    @Test
    public void whenValidateRentingDatesOrder_withSameDates_thenCorrect() {
        //given
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now();

        //when and then
        rentingValidation.throwIfInvalidRentingDatesOrder(dateFrom, dateTo);
    }

    @Test
    public void whenValidateRentingDatesOrder_withDifferentDates_thenCorrect() {
        //given
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(1);

        //when and then
        rentingValidation.throwIfInvalidRentingDatesOrder(dateFrom, dateTo);
    }

    @Test
    public void whenValidateRentingDatesOrder_thenThrow() {
        //given
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().minusDays(1);

        //when and then
        assertThrows(InvalidReservationDataException.class,
                () -> rentingValidation.throwIfInvalidRentingDatesOrder(dateFrom, dateTo));
    }


}
