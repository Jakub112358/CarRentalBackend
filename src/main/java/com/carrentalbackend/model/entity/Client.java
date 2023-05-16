package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client extends User implements CrudEntity {

    @Builder
    public Client(long id, String email, String password, Role role, String firstName, String lastName, Address address, List<Reservation> reservations) {
        super(id, email, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.reservations = reservations;
    }

    private String firstName;
    private String lastName;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;
    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Reservation> reservations;
}
