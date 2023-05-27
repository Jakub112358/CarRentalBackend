package com.carrentalbackend.features.renting.carSearch;

import com.carrentalbackend.features.cars.rest.CarResponse;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarSearchResponse {

    private CarResponse carResponse;
    private BigDecimal price;
}
