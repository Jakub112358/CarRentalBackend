package com.carrentalbackend.model;

import com.carrentalbackend.model.enumeration.JobPosition;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;
    @ManyToOne
    private BranchOffice branchOffice;
    @OneToMany (mappedBy = "employee", cascade = CascadeType.PERSIST)
    private List<CarPickUp> carPickUps;
}
