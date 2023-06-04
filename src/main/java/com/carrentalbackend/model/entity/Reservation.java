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
    private Long id;
    private LocalDateTime reservationDate;
    private BigDecimal price;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;
    @ManyToOne (fetch = FetchType.LAZY)
    private Client client;
    @ManyToOne (fetch = FetchType.LAZY)
    private Car car;
    @ManyToOne (fetch = FetchType.LAZY)
    private Office pickUpOffice;
    @ManyToOne (fetch = FetchType.LAZY)
    private Office returnOffice;
    @OneToOne (cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private PickUp pickUp;
    @OneToOne (cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private CarReturn carReturn;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Reservation other))
            return false;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
