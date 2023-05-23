package com.carrentalbackend.features.cars.rest;

import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequest implements Request, UpdateRequest {
    @NotEmpty
    private String make;
    @NotEmpty
    private String model;
    @Min(1)
    private Integer mileage;
    @Min(1)
    private Integer minRentalTime;
    @Min(1900)
    private Integer yearOfManufacture;
    @NotNull
    private CarBodyType bodyType;
    @NotNull
    private Color color;
    @NotNull
    private CarStatus status;
    @Min(1)
    private Long priceListId;
    @Min(1)
    private Long currentOfficeId;

}
