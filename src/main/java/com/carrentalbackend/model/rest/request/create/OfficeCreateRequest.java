package com.carrentalbackend.model.rest.request.create;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeCreateRequest implements CreateRequest{
    private long id;
    private Address address;
    private Long companyId;
}
