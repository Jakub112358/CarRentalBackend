package com.carrentalbackend.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeDto {

    private long id;
    private BigDecimal incomeValue;

    private Long reservationId;

    private Long financesId;
}
