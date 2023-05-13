package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.updateDto.ClientUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.rest.request.create.ClientCreateRequest;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.ClientResponse;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ClientMapper implements CrudMapper<Client> {


    @Override
    public Client toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, ClientCreateRequest.class);
        ClientCreateRequest clientRequest = (ClientCreateRequest) request;

        return Client.builder()
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .email(clientRequest.getEmail())
                .address(clientRequest.getAddress())
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
    public UpdateDto toUpdateDto(UpdateRequest request) {
        ServiceUtil.checkIfInstance(request, ClientCreateRequest.class);
        ClientCreateRequest clientRequest = (ClientCreateRequest) request;

        return ClientUpdateDto.builder()
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .email(clientRequest.getEmail())
                .address(clientRequest.getAddress())
                .build();
    }
}
