package com.carrentalbackend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Finances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany (mappedBy = "finances", cascade = CascadeType.PERSIST)
    private List<Income> incomes;
    @OneToOne (mappedBy = "finances")
    private CarRentalCompany carRentalCompany;
}
