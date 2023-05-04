package com.carrentalbackend.model.dto;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientUpdateDto implements UpdateDto{
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
