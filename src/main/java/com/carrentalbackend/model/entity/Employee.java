package com.carrentalbackend.model.entity;

import com.carrentalbackend.model.enumeration.JobPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;
    @ManyToOne
    private Office office;
    @OneToMany (mappedBy = "employee", cascade = CascadeType.PERSIST)
    private List<PickUp> pickUps;
}
