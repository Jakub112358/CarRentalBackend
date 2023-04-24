package com.carrentalbackend.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Finances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany (mappedBy = "finances", cascade = CascadeType.ALL)
    private List<Income> incomes;
    @OneToOne (mappedBy = "finances")
    private Company company;
}
