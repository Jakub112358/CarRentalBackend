package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.RentalActionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CarPickUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String comments;
    private LocalDateTime pickUpDate;
    @Enumerated(value = EnumType.STRING)
    RentalActionStatus status;
    @ManyToOne
    private Employee employee;
    @OneToOne (mappedBy = "carPickUp")
    private Reservation reservation;
    @ManyToOne
    private Car car;
}
