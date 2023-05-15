package com.carrentalbackend.model.rest.request.create;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCreateRequest implements CreateRequest{
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
