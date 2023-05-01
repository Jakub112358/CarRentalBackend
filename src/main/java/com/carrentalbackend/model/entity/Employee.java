package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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
