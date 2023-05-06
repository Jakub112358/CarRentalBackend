package com.carrentalbackend.model.dto.crudDto;

import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto implements CrudDto {

    private long id;
    private String make;
    private String model;
    private Integer mileage;
    private Integer minRentalTime;
    private Integer yearOfManufacture;
    private CarBodyType bodyType;
    private Color color;
    private CarStatus status;
    private Long currentBranchOfficeId;
}
