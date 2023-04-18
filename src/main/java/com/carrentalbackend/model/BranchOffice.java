package com.carrentalbackend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class BranchOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Address address;
    @OneToMany(mappedBy = "branchOffice")
    private List<Employee> employees;
    @OneToMany (mappedBy = "currentBranchOffice")
    private List<Car> availableCars;
    @ManyToOne
    private CarRentalCompany carRentalCompany;
    @OneToMany (mappedBy = "pickUpOffice")
    private List<Reservation> pickUpReservations;
    @OneToMany (mappedBy = "returnOffice")
    private List<Reservation> returnReservations;
}
