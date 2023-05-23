package com.carrentalbackend.features.clients.rest;

import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.features.generics.Response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse implements Response {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
