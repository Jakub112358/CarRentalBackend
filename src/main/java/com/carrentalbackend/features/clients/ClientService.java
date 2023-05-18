package com.carrentalbackend.features.clients;

import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.features.clients.register.ClientCreateRequest;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends CrudService<Client, ClientUpdateRequest, ClientCreateRequest> {

    public ClientService(ClientRepository repository, ClientMapper mapper) {
        super(repository, mapper);

    }

    @Override
    public void deleteById(Long id) {

    }
}
