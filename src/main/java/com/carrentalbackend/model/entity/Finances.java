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
public class Finances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "finances", cascade = CascadeType.ALL)
    private List<Income> incomes;
    @OneToOne(mappedBy = "finances", fetch = FetchType.LAZY)
    private Company company;
}
