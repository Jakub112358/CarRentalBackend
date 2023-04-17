package com.carrentalbackend.model;

import javax.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "address_id",referencedColumnName = "id")
    private Address address;
}
