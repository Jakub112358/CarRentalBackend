package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime reservationDate;
    private BigDecimal price;
    @Enumerated(value = EnumType.STRING)
    ReservationStatus status;
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
