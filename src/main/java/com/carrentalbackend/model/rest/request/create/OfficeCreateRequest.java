package com.carrentalbackend.model.rest.request.create;

import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeCreateRequest implements CreateRequest {
    private Address address;
    private Long companyId;
}
