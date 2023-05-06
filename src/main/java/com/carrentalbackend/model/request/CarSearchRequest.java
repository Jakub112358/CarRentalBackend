package com.carrentalbackend.model.request;

import com.carrentalbackend.model.enumeration.Color;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSearchRequest {
    private Color color;
}
