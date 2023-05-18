package com.carrentalbackend.service.mapper;

import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.model.dto.updateDto.ClientUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.enumeration.Role;
import com.carrentalbackend.features.clients.register.ClientCreateRequest;
import com.carrentalbackend.model.rest.request.update.ClientUpdateRequest;
import com.carrentalbackend.features.clients.ClientResponse;
import com.carrentalbackend.features.generics.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ClientMapper implements CrudMapper<Client, ClientUpdateRequest, ClientCreateRequest> {

private final PasswordEncoder passwordEncoder;
    @Override
    public Client toNewEntity(ClientCreateRequest request) {

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

    @Override
    public UpdateDto toUpdateDto(ClientUpdateRequest request) {


        return ClientUpdateDto.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .build();
    }
}
