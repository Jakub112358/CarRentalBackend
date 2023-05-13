package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.IncomeDto;
import com.carrentalbackend.model.entity.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {
    public IncomeDto toDto (Income income){
        Long reservationId = income.getReservation() != null ? income.getReservation().getId() : null;
        Long financesId = income.getFinances() != null ? income.getFinances().getId() : null;
        return IncomeDto.builder()
                .id(income.getId())
                .incomeValue(income.getIncomeValue())
                .reservationId(reservationId)
                .financesId(financesId)
                .build();
    }


}
