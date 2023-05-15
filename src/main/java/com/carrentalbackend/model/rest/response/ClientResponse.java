package com.carrentalbackend.model.rest.response;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse implements Response{
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
