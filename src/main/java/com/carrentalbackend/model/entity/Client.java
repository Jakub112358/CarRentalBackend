package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client extends User implements CrudEntity {

    @Builder
    public Client(Long id, String email, String password, Role role, String firstName, String lastName, Address address, List<Reservation> reservations) {
        super(id, email, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.reservations = reservations;
    }


    private String firstName;

    private String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Reservation> reservations;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Client))
            return false;

        Client other = (Client) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
