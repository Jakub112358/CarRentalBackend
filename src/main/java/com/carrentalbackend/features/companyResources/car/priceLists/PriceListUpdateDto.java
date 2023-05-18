package com.carrentalbackend.features.companyResources.car.priceLists;

import com.carrentalbackend.features.generics.UpdateDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListUpdateDto implements UpdateDto {
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
