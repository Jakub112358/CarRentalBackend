package com.carrentalbackend.features.offices.rest;

import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeResponse implements Response {
    private long id;
    private Address address;
}
