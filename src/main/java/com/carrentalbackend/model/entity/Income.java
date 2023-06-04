package com.carrentalbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne (fetch = FetchType.LAZY)
    private Reservation reservation;
    @ManyToOne (fetch = FetchType.LAZY)
    private Finances finances;
}
