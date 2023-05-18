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
public class Company implements CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String domain;
    private double differentOfficesExtraCharge;
    @Lob
    @Column(length = 65_535)
    @Basic(fetch = FetchType.LAZY)
    private byte[] logotype;
    @OneToOne (cascade = CascadeType.ALL)
    private Address address;
    @OneToMany (mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<Office> offices;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Finances finances;



}
