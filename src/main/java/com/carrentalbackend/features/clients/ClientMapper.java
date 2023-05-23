package com.carrentalbackend.features.clients;

import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.enumeration.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ClientMapper implements CrudMapper<Client, ClientCreateUpdateRequest, ClientCreateUpdateRequest> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Client toNewEntity(ClientCreateUpdateRequest request) {

        return Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .reservations(new ArrayList<>())
                .email(request.getEmail())
                .role(Role.CLIENT)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    @Override
    public Response toResponse(Client entity) {
        return ClientResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .build();
    }
}
