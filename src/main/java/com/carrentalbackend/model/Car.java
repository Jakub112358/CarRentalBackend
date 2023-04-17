package com.carrentalbackend.model;

import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String make;
    private String model;
    private CarBodyType bodyType;
    private LocalDate yearOfManufacture;
    private Color color;
    private int mileage;
    private CarStatus status;
    @OneToOne
    private Pricelist pricelist;
    private int minRentalTime;

}
