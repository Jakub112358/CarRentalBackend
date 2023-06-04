package com.carrentalbackend.features.clients.rest;

import com.carrentalbackend.exception.ExistingEmailException;
import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClientUpdateTool implements UpdateTool<Client, ClientUpdateRequest> {
    private final UserRepository userRepository;

    @Override
    public void updateEntity(Client entity, ClientUpdateRequest updateRequest) {
        throwIfInvalidEmail(entity, updateRequest);

        entity.setFirstName(updateRequest.getFirstName());
        entity.setLastName(updateRequest.getLastName());
        entity.setEmail(updateRequest.getEmail());
        entity.setAddress(updateRequest.getAddress());
    }

    private void throwIfInvalidEmail(Client entity, ClientUpdateRequest updateRequest) {
        if (entity.getEmail().equals(updateRequest.getEmail()))
            return;
        if (userRepository.existsByEmail(updateRequest.getEmail()))
            throw new ExistingEmailException(updateRequest.getEmail());
    }
}
