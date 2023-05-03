package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car implements CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String make;
    private String model;
    private int mileage;
    private int minRentalTime;
    private int yearOfManufacture;
    @Enumerated(EnumType.STRING)
    private CarBodyType bodyType;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    @ManyToOne
    private Pricelist pricelist;
    @ManyToOne
    private BranchOffice currentBranchOffice;

}
