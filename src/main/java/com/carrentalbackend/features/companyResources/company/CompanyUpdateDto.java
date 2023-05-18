package com.carrentalbackend.features.companyResources.company;

import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUpdateDto implements UpdateDto {
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;
}
