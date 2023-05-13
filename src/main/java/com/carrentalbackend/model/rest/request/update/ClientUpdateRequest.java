package com.carrentalbackend.model.rest.request.update;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientUpdateRequest implements UpdateRequest{
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
