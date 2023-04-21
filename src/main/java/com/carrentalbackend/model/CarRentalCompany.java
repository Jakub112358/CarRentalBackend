package com.carrentalbackend.model;

import javax.persistence.*;
import java.util.List;
@Entity
public class CarRentalCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String domain;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] logotype;
    @OneToOne (cascade = CascadeType.ALL)
    private Address address;
    @OneToMany (mappedBy = "carRentalCompany", cascade = CascadeType.PERSIST)
    private List<BranchOffice> branchOffices;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Finances finances;


}
