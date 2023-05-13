package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.crudDto.ClientDto;
import com.carrentalbackend.model.dto.updateDto.ClientUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ClientMapper implements CrudMapper<Client, ClientDto> {
    @Override
    public Client toNewEntity(ClientDto dto) {
        return Client.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .reservations(new ArrayList<>())
                .build();
    }

    @Override
    public UpdateDto toUpdateEntity(ClientDto dto) {
        return ClientUpdateDto.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .build();
    }

    @Override
    public ClientDto toDto(Client entity) {
        return ClientDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .build();
    }

    @Override
    public Client toNewEntity(CreateRequest request) {
        return null;
    }

    @Override
    public Response toCreateResponse(Client entity) {
        return null;
    }
}
