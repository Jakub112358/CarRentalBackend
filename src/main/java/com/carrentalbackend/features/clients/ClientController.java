package com.carrentalbackend.features.clients;

import com.carrentalbackend.features.clients.register.ClientCreateRequest;
import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.model.rest.request.update.ClientUpdateRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.config.ApiConstraints.CLIENT;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(CLIENT)
@CrossOrigin(origins = ORIGIN)
public class ClientController extends CrudController<ClientCreateRequest, ClientUpdateRequest> {
    public ClientController(ClientService service) {
        super(service);
    }
}
