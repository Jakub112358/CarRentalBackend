package com.carrentalbackend.model.rest.request.update;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeUpdateRequest implements UpdateRequest{
    private Address address;
    private Long companyId;
}
