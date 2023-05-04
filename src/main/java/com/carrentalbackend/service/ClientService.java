package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.ClientDto;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.mapper.ClientMapper;
import com.carrentalbackend.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends CrudService<Client, ClientDto> {
    private final ClientRepository repository;
    public ClientService(ClientRepository repository, ClientMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public void deleteById(Long id) {

    }
}
