package com.carrentalbackend.features.companyResources.companies.rest;

import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest implements Request, UpdateRequest {
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;
    private double differentOfficesExtraCharge;
}
