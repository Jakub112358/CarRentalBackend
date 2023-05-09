package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.RentalActionStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPickUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String comments;
    private LocalDate pickUpDate;
    private LocalDate plannedPickUpDate;
    @Enumerated(value = EnumType.STRING)
    RentalActionStatus status;
    @ManyToOne
    private Employee employee;
    @OneToOne(mappedBy = "carPickUp")
    private Reservation reservation;
    @ManyToOne
    private Car car;
    @ManyToOne
    private BranchOffice branchOffice;
}
