package com.carrentalbackend.model.rest;

import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRentResponse {

    private long id;
    private String make;
    private String model;
    private Integer mileage;
    private Integer minRentalTime;
    private Integer yearOfManufacture;
    private CarBodyType bodyType;
    private Color color;
    private CarStatus status;
    private Long priceListId;
    private Long currentBranchOfficeId;
    private BigDecimal price;
}
