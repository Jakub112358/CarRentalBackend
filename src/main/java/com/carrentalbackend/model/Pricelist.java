package com.carrentalbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pricelist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double pricePerDay;
    private double pricePerWeek;
    private double pricePerMonth;
}
