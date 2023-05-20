package com.carrentalbackend.features.companyResources.car.priceLists;

import com.carrentalbackend.features.generics.CreateRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListCreateRequest implements CreateRequest {
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}