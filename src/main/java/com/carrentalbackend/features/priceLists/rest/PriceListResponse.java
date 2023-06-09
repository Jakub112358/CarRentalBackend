package com.carrentalbackend.features.priceLists.rest;

import com.carrentalbackend.features.generics.Response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListResponse implements Response {
    private long id;
    private Double shortTermPrice;
    private Double mediumTermPrice;
    private Double longTermPrice;
}
