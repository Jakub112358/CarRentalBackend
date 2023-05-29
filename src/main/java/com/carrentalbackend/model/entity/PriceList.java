package com.carrentalbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private double shortTermPrice;
    private double mediumTermPrice;
    private double longTermPrice;
    @OneToMany(mappedBy = "priceList", cascade = CascadeType.PERSIST)
    private List<Car> cars;
}
