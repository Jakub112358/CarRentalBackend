package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.ClientDto;
import com.carrentalbackend.model.dto.ClientUpdateDto;
import com.carrentalbackend.model.dto.UpdateDto;
import com.carrentalbackend.model.entity.Client;
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
}
