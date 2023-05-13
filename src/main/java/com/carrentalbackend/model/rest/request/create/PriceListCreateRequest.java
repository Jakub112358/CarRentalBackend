package com.carrentalbackend.model.rest.request.create;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListCreateRequest implements CreateRequest{
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
