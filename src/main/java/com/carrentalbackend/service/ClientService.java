package com.carrentalbackend.service;

import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.rest.request.create.ClientCreateRequest;
import com.carrentalbackend.model.rest.request.update.ClientUpdateRequest;
import com.carrentalbackend.service.mapper.ClientMapper;
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
