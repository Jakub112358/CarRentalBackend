package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.RentalActionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickUp implements CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comments;
    private LocalDate pickUpDate;
    private LocalDate plannedPickUpDate;
    @Enumerated(value = EnumType.STRING)
    private RentalActionStatus status;
    @ManyToOne (fetch = FetchType.LAZY)
    private Employee employee;
    @OneToOne(mappedBy = "pickUp", fetch = FetchType.LAZY)
    private Reservation reservation;
    @ManyToOne (fetch = FetchType.LAZY)
    private Car car;
    @ManyToOne (fetch = FetchType.LAZY)
    private Office office;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof PickUp))
            return false;

        PickUp other = (PickUp) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
