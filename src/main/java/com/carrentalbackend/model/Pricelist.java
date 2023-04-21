package com.carrentalbackend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pricelist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double pricePerDay;
    private double pricePerWeek;
    private double pricePerMonth;
    @OneToMany (mappedBy = "pricelist", cascade = CascadeType.PERSIST)
    private List<Car> cars;
}
