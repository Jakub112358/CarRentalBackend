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
public class Office implements CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "office", cascade = CascadeType.PERSIST)
    private List<Employee> employees;
    @OneToMany (mappedBy = "currentOffice", cascade = CascadeType.PERSIST)
    private List<Car> availableCars;
    @ManyToOne
    private Company company;
    @OneToMany (mappedBy = "pickUpOffice", cascade = CascadeType.PERSIST)
    private List<Reservation> pickUpReservations;
    @OneToMany (mappedBy = "returnOffice", cascade = CascadeType.PERSIST)
    private List<Reservation> returnReservations;

}
