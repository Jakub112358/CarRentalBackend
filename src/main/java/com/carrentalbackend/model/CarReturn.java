package com.carrentalbackend.model;

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
    @ManyToOne
    private Employee employee;
    @OneToOne (mappedBy = "carReturn")
    private Reservation reservation;
    @ManyToOne
    private Car car;

}
