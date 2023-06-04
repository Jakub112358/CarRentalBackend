package com.carrentalbackend.features.clients.rest;

import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.entity.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientUpdateRequest implements UpdateRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @Valid
    @NotNull
    private Address address;
    @NotEmpty
    private String password;
}
