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
public class PriceList implements CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double pricePerDay;
    private double pricePerWeek;
    private double pricePerMonth;
    @OneToMany (mappedBy = "priceList", cascade = CascadeType.PERSIST)
    private List<Car> cars;
}
