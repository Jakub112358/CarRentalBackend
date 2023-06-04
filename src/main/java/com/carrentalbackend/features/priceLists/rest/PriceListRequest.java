package com.carrentalbackend.features.priceLists.rest;

import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.features.generics.UpdateRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListRequest implements CreateRequest, UpdateRequest {
    @NotNull
    @Min(0)
    private Double shortTermPrice;
    @NotNull
    @Min(0)
    private Double mediumTermPrice;
    @NotNull
    @Min(0)
    private Double longTermPrice;
}
