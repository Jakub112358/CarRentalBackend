package com.carrentalbackend.model.dto.crudDto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeDto implements CrudDto {

    private long id;
    private BigDecimal incomeValue;

    private Long reservationId;

    private Long financesId;
}
