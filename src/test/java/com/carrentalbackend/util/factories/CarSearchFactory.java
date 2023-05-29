package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.cars.rest.CarResponse;
import com.carrentalbackend.features.renting.carSearch.CarSearchResponse;

import java.math.BigDecimal;

import static com.carrentalbackend.features.renting.carSearch.CarSearchResponse.CarSearchResponseBuilder;

public class CarSearchFactory {
    public final static BigDecimal simpleCarSearchPrice = BigDecimal.valueOf(100);

    public static CarSearchResponseBuilder getSimpleCarSearchResponseBuilder(CarResponse carResponse) {
        return CarSearchResponse.builder()
                .carResponse(carResponse)
                .price(simpleCarSearchPrice);

    }
}
