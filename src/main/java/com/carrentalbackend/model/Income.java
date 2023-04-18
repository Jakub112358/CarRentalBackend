package com.carrentalbackend.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal incomeValue;
    @ManyToOne
    private Reservation reservation;
    @ManyToOne
    private Finances finances;
}
