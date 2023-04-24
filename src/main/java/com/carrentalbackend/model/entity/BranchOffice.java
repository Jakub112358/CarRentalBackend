package com.carrentalbackend.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class BranchOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "branchOffice", cascade = CascadeType.PERSIST)
    private List<Employee> employees;
    @OneToMany (mappedBy = "currentBranchOffice", cascade = CascadeType.PERSIST)
    private List<Car> availableCars;
    @ManyToOne
    private Company company;
    @OneToMany (mappedBy = "pickUpOffice", cascade = CascadeType.PERSIST)
    private List<Reservation> pickUpReservations;
    @OneToMany (mappedBy = "returnOffice", cascade = CascadeType.PERSIST)
    private List<Reservation> returnReservations;
}
