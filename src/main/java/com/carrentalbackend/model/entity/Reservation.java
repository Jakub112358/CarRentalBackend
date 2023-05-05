package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.ReservationStatus;
import lombok.*;

import javax.persistence.*;
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
    @OneToOne
    private Car car;
    @ManyToOne
    private BranchOffice pickUpOffice;
    @ManyToOne
    private BranchOffice returnOffice;
    @OneToOne (cascade = CascadeType.PERSIST)
    private CarPickUp carPickUp;
    @OneToOne (cascade = CascadeType.PERSIST)
    private CarReturn carReturn;



}
