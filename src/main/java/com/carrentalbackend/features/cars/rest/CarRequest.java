package com.carrentalbackend.features.cars.rest;

import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import com.carrentalbackend.validation.existingOfficeId.ExistingOfficeId;
import com.carrentalbackend.validation.existingPriceListId.ExistingPriceListId;
import com.carrentalbackend.validation.yearOfManufacture.YearOfManufacture;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequest implements CreateRequest, UpdateRequest {
    @NotEmpty
    private String make;
    @NotEmpty
    private String model;
    @Min(1)
    @NotNull
    private Integer mileage;
    @Min(1)
    @NotNull
    private Integer minRentalTime;
    @YearOfManufacture
    private Integer yearOfManufacture;
    @NotNull
    private CarBodyType bodyType;
    @NotNull
    private Color color;
    @NotNull
    private CarStatus status;
    @ExistingPriceListId
    private Long priceListId;
    @ExistingOfficeId
    private Long currentOfficeId;

}
