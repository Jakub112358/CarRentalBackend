package com.carrentalbackend.features.renting.pickUps;

import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PickUpUpdateDto implements UpdateDto {
    private String comments;
    private LocalDate pickUpDate;
    private LocalDate plannedPickUpDate;
    private RentalActionStatus status;
    private Employee employee;
    private Reservation reservation;
    private Car car;
    private Office office;
}
