package com.carrentalbackend.model.dto.updateDto;

import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.entity.Pricelist;
import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarUpdateDto implements UpdateDto {
    private String make;
    private String model;
    private Integer mileage;
    private Integer minRentalTime;
    private Integer yearOfManufacture;
    private CarBodyType bodyType;
    private Color color;
    private CarStatus status;
    private Pricelist pricelist;
    private BranchOffice currentBranchOffice;
}
