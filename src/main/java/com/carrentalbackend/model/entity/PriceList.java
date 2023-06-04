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
    private Long id;
    private double shortTermPrice;
    private double mediumTermPrice;
    private double longTermPrice;
    @OneToMany(mappedBy = "priceList", cascade = CascadeType.PERSIST)
    private List<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof PriceList other))
            return false;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
