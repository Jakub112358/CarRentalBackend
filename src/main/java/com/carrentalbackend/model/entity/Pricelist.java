package com.carrentalbackend.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pricelist implements CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double pricePerDay;
    private double pricePerWeek;
    private double pricePerMonth;
    @OneToMany (mappedBy = "pricelist", cascade = CascadeType.PERSIST)
    private List<Car> cars;
}
