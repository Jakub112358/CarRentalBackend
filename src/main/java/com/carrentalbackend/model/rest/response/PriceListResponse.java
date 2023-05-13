package com.carrentalbackend.model.rest.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListResponse implements Response{
    private long id;
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
