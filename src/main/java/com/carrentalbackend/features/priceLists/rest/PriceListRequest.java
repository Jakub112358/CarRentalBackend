package com.carrentalbackend.features.priceLists.rest;

import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.features.generics.UpdateRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListRequest implements Request, UpdateRequest {
    @NotNull
    @Min(0)
    private Double pricePerDay;
    @NotNull
    @Min(0)
    private Double pricePerWeek;
    @NotNull
    @Min(0)
    private Double pricePerMonth;
}
