package com.carrentalbackend.features.companyResources.office;

import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeRequest implements Request, UpdateRequest {
    private Address address;
    private Long companyId;
}
