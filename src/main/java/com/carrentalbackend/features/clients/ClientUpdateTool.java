package com.carrentalbackend.features.clients;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientUpdateTool implements UpdateTool <Client, ClientCreateUpdateRequest> {
    @Override
    public void updateEntity(Client entity, ClientCreateUpdateRequest updateRequest) {
        entity.setFirstName(updateRequest.getFirstName());
        entity.setLastName(updateRequest.getLastName());
        entity.setEmail(updateRequest.getEmail());
        entity.setAddress(updateRequest.getAddress());
    }
}
