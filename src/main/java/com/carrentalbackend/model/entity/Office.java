package com.carrentalbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Office implements CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "office", cascade = CascadeType.PERSIST)
    private List<Employee> employees;
    @OneToMany (mappedBy = "currentOffice", cascade = CascadeType.PERSIST)
    private List<Car> availableCars;
    @OneToMany (mappedBy = "pickUpOffice", cascade = CascadeType.PERSIST)
    private List<Reservation> pickUpReservations;
    @OneToMany (mappedBy = "returnOffice", cascade = CascadeType.PERSIST)
    private List<Reservation> returnReservations;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Office))
            return false;

        Office other = (Office) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
