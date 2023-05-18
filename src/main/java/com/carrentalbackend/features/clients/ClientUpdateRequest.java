package com.carrentalbackend.features.clients;

import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.features.generics.UpdateRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientUpdateRequest implements UpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
