package com.carrentalbackend.features.companyResources.car.priceLists;

import com.carrentalbackend.features.generics.Response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListResponse implements Response {
    private long id;
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
