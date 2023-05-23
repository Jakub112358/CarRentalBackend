package com.carrentalbackend.features.priceLists.rest;

import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.features.generics.UpdateRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListRequest implements Request, UpdateRequest {
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
