package com.carrentalbackend.features.renting;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.renting.carSearch.CarSearchResponse;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.repository.CompanyRepository;
import com.carrentalbackend.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class RentingUtil {
    private final RentingValidation rentingValidation;
    private final CompanyRepository companyRepository;
    private final PriceListRepository priceListRepository;
    public int calculateRentalLength(LocalDate dateFrom, LocalDate dateTo) {
        rentingValidation.validateRentingDates(dateFrom, dateTo);
        return (int) DAYS.between(dateFrom, dateTo) + 1;
    }

    public BigDecimal calculatePrice(CarSearchResponse rentResponse, int rentalLength, boolean sameOffices) {
        PriceList priceList = getPriceList(rentResponse);
        double currentPrice = getCurrentPrice(rentalLength, priceList);
        return calculateTotalPrice(rentalLength, currentPrice, sameOffices);
    }

    private BigDecimal calculateTotalPrice(int rentalLength, double currentPrice, boolean sameOffices) {
        double extraCharge;
        if (sameOffices) {
            extraCharge = 0.0;
        } else {
            extraCharge = companyRepository
                    .findFirstByIdIsNotNull()
                    .orElseThrow(() -> new ResourceNotFoundException(1L))
                    .getDifferentOfficesExtraCharge();
        }
        return BigDecimal.valueOf(rentalLength * currentPrice + extraCharge);
    }

    private double getCurrentPrice(int rentalLength, PriceList priceList) {
        if (rentalLength < 7)
            return priceList.getPricePerDay();
        else if (rentalLength < 30)
            return priceList.getPricePerWeek();
        else
            return priceList.getPricePerMonth();
    }

    private PriceList getPriceList(CarSearchResponse rentDto) {
        return priceListRepository.findById(rentDto.getPriceListId())
                .orElseThrow(() -> new ResourceNotFoundException(rentDto.getPriceListId()));
    }
}
