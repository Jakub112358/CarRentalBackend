package com.carrentalbackend.features.companyResources.office;

import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.entity.Company;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeUpdateDto implements UpdateDto {

    private Address address;
    private Company company;
}
