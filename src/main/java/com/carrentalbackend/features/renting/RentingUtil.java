package com.carrentalbackend.features.renting;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.renting.carSearch.CarSearchResponse;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.CompanyRepository;
import com.carrentalbackend.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class RentingUtil {
    private final RentingValidation rentingValidation;
    private final CompanyRepository companyRepository;
    private final PriceListRepository priceListRepository;
    private final CarRepository carRepository;
    public int calculateRentalLength(LocalDate dateFrom, LocalDate dateTo) {
        rentingValidation.throwIfInvalidRentingDatesOrder(dateFrom, dateTo);
        return (int) DAYS.between(dateFrom, dateTo) + 1;
    }

    public BigDecimal calculatePrice(CarSearchResponse rentResponse, int rentalLength, boolean sameOffices) {
        PriceList priceList = getPriceList(rentResponse);
        return calculatePrice(priceList, rentalLength, sameOffices);
    }

    public BigDecimal calculatePrice(Long carId, int rentalLength, boolean sameOffices) {
        PriceList priceList = getPriceList(carId);
        return calculatePrice(priceList, rentalLength, sameOffices);
    }


    public BigDecimal calculatePrice(PriceList priceList, int rentalLength, boolean sameOffices) {
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
        Company company = companyRepository.findFirstByIdIsNotNull().orElseThrow(()-> new ResourceNotFoundException(1L));
        int mediumTermMinDays = company.getMediumTermRentMinDays();
        int longTermMinDays = company.getLongTermRentMinDays();

        if (rentalLength < mediumTermMinDays)
            return priceList.getShortTermPrice();
        else if (rentalLength < longTermMinDays)
            return priceList.getMediumTermPrice();
        else
            return priceList.getLongTermPrice();
    }

    private PriceList getPriceList(CarSearchResponse rentDto) {
        Long priceListId = rentDto.getCarResponse().getPriceListId();
        return priceListRepository.findById(priceListId)
                .orElseThrow(() -> new ResourceNotFoundException(priceListId));
    }

    private PriceList getPriceList(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(()-> new ResourceNotFoundException(carId));
        Long priceListId = car.getPriceList().getId();
        return priceListRepository.findById(priceListId)
                .orElseThrow(() -> new ResourceNotFoundException(priceListId));
    }

    public BigDecimal calculateCashback(Reservation reservation) {
        BigDecimal price = reservation.getPrice();
        double extraChargeRatio = calculateExtraChargeRatio(reservation);
        return BigDecimal.valueOf(price.doubleValue() * (1.0 - extraChargeRatio) * (-1)).setScale(2, RoundingMode.CEILING);
    }

    private double calculateExtraChargeRatio(Reservation reservation) {
        Company company = companyRepository.findFirstByIdIsNotNull().orElseThrow(()->new ResourceNotFoundException(1L));
        LocalDate dateNow = LocalDate.now();
        LocalDate reservationStart = reservation.getDateFrom();
        long daysDifference = DAYS.between(dateNow, reservationStart);

        if (daysDifference >= company.getFreeCancellationDaysLimit()) {
            return 0;
        } else {
            return company.getLateCancellationRatio();
        }
    }


}
