package com.carrentalbackend.model;

import com.carrentalbackend.model.enumeration.JobPosition;

import javax.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    @ManyToOne
    private BranchOffice branchOffice;
}
