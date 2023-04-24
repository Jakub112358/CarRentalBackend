package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.RentalActionStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class CarReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String comments;
    private BigDecimal extraCharge;
    private LocalDateTime returnDate;
    @Enumerated(value = EnumType.STRING)
    RentalActionStatus status;
    @ManyToOne
    private Employee employee;
    @OneToOne (mappedBy = "carReturn")
    private Reservation reservation;
    @ManyToOne
    private Car car;

}
