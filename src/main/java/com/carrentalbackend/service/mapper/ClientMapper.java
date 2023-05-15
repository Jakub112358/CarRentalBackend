package com.carrentalbackend.service.mapper;

import com.carrentalbackend.model.dto.updateDto.ClientUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.rest.request.create.ClientCreateRequest;
import com.carrentalbackend.model.rest.request.update.ClientUpdateRequest;
import com.carrentalbackend.model.rest.response.ClientResponse;
import com.carrentalbackend.model.rest.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ClientMapper implements CrudMapper<Client, ClientUpdateRequest, ClientCreateRequest> {


    @Override
    public Client toNewEntity(ClientCreateRequest request) {

        return Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .reservations(new ArrayList<>())
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
