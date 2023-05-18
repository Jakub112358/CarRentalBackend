package com.carrentalbackend.features.companyResources.company;

import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.features.generics.UpdateRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUpdateRequest implements UpdateRequest {
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;
    private double differentOfficesExtraCharge;
}
