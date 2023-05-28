package com.carrentalbackend.features.renting.carSearch;

import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.Color;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSearchByCriteriaRequest {
    private Set<String> makeOf;
    private Set<String> modelOf;
    private Integer maxMileage;
    private Integer minYearOfManufacture;
    private Set<CarBodyType> bodyTypeOf;
    private Set<Color> colorOf;
}
