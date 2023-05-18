package com.carrentalbackend.features.renting.carReturns;

import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarReturnUpdateDto implements UpdateDto {
    private String comments;
    private BigDecimal extraCharge;
    private LocalDate returnDate;
    private RentalActionStatus status;
    private Employee employee;
}
