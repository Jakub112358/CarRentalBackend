package com.carrentalbackend.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime reservationDate;
    private BigDecimal price;
    @ManyToOne
    private Client client;
    @OneToOne
    private Car car;
    @ManyToOne
    private BranchOffice pickUpOffice;
    @ManyToOne
    private BranchOffice returnOffice;
    @OneToOne
    private CarPickUp carPickUp;
    @OneToOne
    private CarReturn carReturn;



}
