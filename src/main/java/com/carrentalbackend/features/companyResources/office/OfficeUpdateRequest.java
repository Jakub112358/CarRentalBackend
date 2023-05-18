package com.carrentalbackend.features.companyResources.office;

import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.features.generics.UpdateRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeUpdateRequest implements UpdateRequest {
    private Address address;
    private Long companyId;
}
