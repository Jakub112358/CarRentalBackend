package com.carrentalbackend.model.dto;

import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto implements CrudDto{

    private long id;
    private String make;
    private String model;
    private int mileage;
    private int minRentalTime;
    private int yearOfManufacture;
    private CarBodyType bodyType;
    private Color color;
    private CarStatus status;
    private long currentBranchOfficeId;
}
