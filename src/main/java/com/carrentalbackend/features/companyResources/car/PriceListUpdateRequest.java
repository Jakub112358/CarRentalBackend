package com.carrentalbackend.features.companyResources.car;

import com.carrentalbackend.features.generics.UpdateRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListUpdateRequest implements UpdateRequest {
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
