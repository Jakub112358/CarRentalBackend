package com.carrentalbackend.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
