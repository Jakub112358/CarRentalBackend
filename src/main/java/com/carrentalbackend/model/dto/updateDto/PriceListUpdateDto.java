package com.carrentalbackend.model.dto.updateDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListUpdateDto implements UpdateDto{
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
