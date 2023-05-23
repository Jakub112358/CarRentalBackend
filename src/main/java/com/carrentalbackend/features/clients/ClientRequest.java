package com.carrentalbackend.features.clients;

import com.carrentalbackend.features.clients.validation.UniqueEmail;
import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.model.entity.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequest implements Request {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    @UniqueEmail
    private String email;
    @Valid
    private Address address;
    @NotEmpty
    private String password;
}