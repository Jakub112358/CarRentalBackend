package com.carrentalbackend.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client implements CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToOne
    private Address address;
    @OneToMany (mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Reservation> reservations;
}
