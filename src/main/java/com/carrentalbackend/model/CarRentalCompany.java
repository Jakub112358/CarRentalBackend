package com.carrentalbackend.model;

import javax.persistence.*;
import java.util.List;

public class CarRentalCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String domain;
    private Address address;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] logotype;
    @OneToMany
    private List<BranchOffice> branchOffices;


}
