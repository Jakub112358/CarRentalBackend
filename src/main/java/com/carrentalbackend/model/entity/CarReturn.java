package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.RentalActionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarReturn implements CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comments;
    private BigDecimal extraCharge;
    private LocalDate returnDate;
    private LocalDate plannedReturnDate;
    @Enumerated(value = EnumType.STRING)
    private RentalActionStatus status;
    @ManyToOne (fetch = FetchType.LAZY)
    private Employee employee;
    @OneToOne(mappedBy = "carReturn", fetch = FetchType.LAZY)
    private Reservation reservation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof CarReturn))
            return false;

        CarReturn other = (CarReturn) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
