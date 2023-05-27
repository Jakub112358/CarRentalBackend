package com.carrentalbackend.features.renting.carSearch;

import com.carrentalbackend.model.enumeration.Color;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSearchByCriteriaRequest {
    private Color color;
}
