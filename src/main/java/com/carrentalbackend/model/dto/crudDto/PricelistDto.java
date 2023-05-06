package com.carrentalbackend.model.dto.crudDto;

import com.carrentalbackend.model.dto.crudDto.CrudDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricelistDto implements CrudDto {
    private long id;
    private Double pricePerDay;
    private Double pricePerWeek;
    private Double pricePerMonth;
}
