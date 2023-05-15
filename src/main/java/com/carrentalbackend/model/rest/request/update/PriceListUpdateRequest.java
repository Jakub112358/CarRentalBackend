package com.carrentalbackend.model.rest.request.update;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListUpdateRequest implements UpdateRequest{
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
