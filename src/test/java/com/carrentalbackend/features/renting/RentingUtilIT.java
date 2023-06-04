package com.carrentalbackend.features.renting;

import com.carrentalbackend.BaseIT;
import com.carrentalbackend.exception.InvalidReservationDataException;
import com.carrentalbackend.features.renting.carSearch.CarSearchResponse;
import com.carrentalbackend.util.factories.CarSearchFactory;
import com.carrentalbackend.util.factories.CompanyFactory;
import com.carrentalbackend.util.factories.PriceListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RentingUtilIT extends BaseIT {

    @BeforeEach
    void setUp() {
        dbOperations.cleanCompanyTable();
        dbOperations.addSimpleCompanyToDB();
    }

    @Test
    public void whenCalculateRentalLength_thenCorrectAnswer() {
        //given
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = dateFrom.plusDays(1);

        //when
        var result = rentingUtil.calculateRentalLength(dateFrom, dateTo);

        //then
        assertEquals(2, result);
    }

    @Test
    public void whenCalculateRentalLength_thenThrow() {
        //given
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = dateFrom.minusDays(1);

        //when and then
        assertThrows(InvalidReservationDataException.class,
                () -> rentingUtil.calculateRentalLength(dateFrom, dateTo));
    }

    @Test
    public void whenCalculatePrice_withSameOffices_withShortTerm_thenCorrectAnswer() {
        //given
        var carSearchResponse = getCarSearchResponse();
        var rentalLength = 1;
        var sameOffices = true;

        //and
        var shortTermRentPrice = PriceListFactory.simplePriceListShortTermPrice;

        //and
        var expected = BigDecimal.valueOf(shortTermRentPrice * rentalLength);

        //when
        var result = rentingUtil.calculatePrice(carSearchResponse, rentalLength, sameOffices);

        //then
        assertEquals(expected, result);
    }

    @Test
    public void whenCalculatePrice_withDifferentOffices_withShortTerm_thenCorrectAnswer() {
        //given
        var carSearchResponse = getCarSearchResponse();
        var rentalLength = 1;
        var sameOffices = false;

        //and
        var shortTermRentPrice = PriceListFactory.simplePriceListShortTermPrice;
        var differentOfficesExtraCharge = CompanyFactory.simpleCompanyDifferentOfficesExtraCharge;

        //and
        var expected = BigDecimal.valueOf(shortTermRentPrice * rentalLength + differentOfficesExtraCharge);

        //when
        var result = rentingUtil.calculatePrice(carSearchResponse, rentalLength, sameOffices);

        //then
        assertEquals(expected, result);
    }

    @Test
    public void whenCalculatePrice_withSameOffices_withMediumTerm_thenCorrectAnswer() {
        //given
        var carSearchResponse = getCarSearchResponse();
        var rentalLength = 7;
        var sameOffices = true;

        //and
        var mediumTermRentPrice = PriceListFactory.simplePriceListMediumTermPrice;

        //and
        var expected = BigDecimal.valueOf(mediumTermRentPrice * rentalLength);

        //when
        var result = rentingUtil.calculatePrice(carSearchResponse, rentalLength, sameOffices);

        //then
        assertEquals(expected, result);
    }

    @Test
    public void whenCalculatePrice_withSameOffices_withLongTerm_thenCorrectAnswer() {
        //given
        var carSearchResponse = getCarSearchResponse();
        var rentalLength = 30;
        var sameOffices = true;

        //and
        var longTermRentPrice = PriceListFactory.simplePriceListLongTermPrice;

        //and
        var expected = BigDecimal.valueOf(longTermRentPrice * rentalLength);

        //when
        var result = rentingUtil.calculatePrice(carSearchResponse, rentalLength, sameOffices);

        //then
        assertEquals(expected, result);
    }

    private CarSearchResponse getCarSearchResponse() {
        var priceList = dbOperations.addSimplePriceListToDb();
        var office = dbOperations.addSimpleOfficeToDB();
        var car = dbOperations.addSimpleCarToDb(office, priceList);

        var carResponse = carMapper.toResponse(car);
        return CarSearchFactory.getSimpleCarSearchResponseBuilder(carResponse).build();
    }


}
