package com.carrentalbackend.features.clients.register;

import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.features.clients.register.validation.UniqueEmail;
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
public class ClientCreateRequest implements CreateRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email @UniqueEmail
    private String email;
    @Valid
    private Address address;
    @NotEmpty
    private String password;

}
