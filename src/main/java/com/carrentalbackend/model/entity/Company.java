package com.carrentalbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company implements CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String domain;
    private double differentOfficesExtraCharge;
    private int mediumTermRentMinDays;
    private int longTermRentMinDays;
    private int freeCancellationDaysLimit;
    private double lateCancellationRatio;
    @Lob
    @Column(length = 65_535)
    @Basic(fetch = FetchType.LAZY)
    private byte[] logotype;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Finances finances;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Company other))
            return false;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
