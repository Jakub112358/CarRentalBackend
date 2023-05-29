package com.carrentalbackend.features.companies.rest;

import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse implements Response {
    private long id;
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;
    private double differentOfficesExtraCharge;
    private int mediumTermRentMinDays;
    private int longTermRentMinDays;
}
