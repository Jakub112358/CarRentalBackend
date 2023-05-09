package com.carrentalbackend.model.dto.updateDto;

import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationUpdateDto implements UpdateDto {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Client client;
    private BranchOffice pickUpOffice;
    private BranchOffice returnOffice;
    private ReservationStatus status;
}
