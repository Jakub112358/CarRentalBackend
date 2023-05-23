package com.carrentalbackend.features.clients;

import com.carrentalbackend.exception.ForbiddenResourceException;
import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.features.clients.register.ClientCreateRequest;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends CrudService<Client, ClientUpdateRequest, ClientCreateRequest> {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository repository, ClientMapper mapper) {
        super(repository, mapper);
        this.clientRepository = repository;

    }

    @Override
    public void deleteById(Long id) {

    }

    public void checkIfIdMatchesUser(Long id, String name) {
        Client client = clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id));
        if(!client.getEmail().equals(name))
            throw new ForbiddenResourceException("client", id);
    }
}
