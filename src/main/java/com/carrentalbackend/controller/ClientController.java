package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.ClientDto;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.service.ClientService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.CLIENT;

@RestController
@RequestMapping(CLIENT)
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController extends CrudController<Client, ClientDto> {
    public ClientController(ClientService service) {
        super(service);
    }
}
