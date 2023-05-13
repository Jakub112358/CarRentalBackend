package com.carrentalbackend.model.rest.request.create;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyCreateRequest implements CreateRequest{
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;
    private double differentOfficesExtraCharge;
}
