package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime reservationDate;
    private BigDecimal price;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Car car;
    @ManyToOne
    private Office pickUpOffice;
    @ManyToOne
    private Office returnOffice;
    @OneToOne (cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private PickUp pickUp;
    @OneToOne (cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private CarReturn carReturn;



}
