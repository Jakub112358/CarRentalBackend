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
    @OneToOne
    private Address address;
    @OneToMany (mappedBy = "carRentalCompany")
    private List<BranchOffice> branchOffices;
    @OneToOne
    private Finances finances;


}
