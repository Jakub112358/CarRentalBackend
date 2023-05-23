package com.carrentalbackend.features.clients;

import com.carrentalbackend.features.clients.rest.ClientRequest;
import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.generics.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.carrentalbackend.config.ApiConstraints.CLIENT;
import static com.carrentalbackend.config.ApiConstraints.ORIGIN;

@RestController
@RequestMapping(CLIENT)
@CrossOrigin(origins = ORIGIN)
public class ClientController extends CrudController<ClientRequest> {
    private final ClientService clientService;

    public ClientController(ClientService service) {
        super(service);
        this.clientService = service;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id, Authentication auth) {
        clientService.throwIfNotPermitted(id, auth);
        return super.findById(id, auth);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @Valid @RequestBody ClientRequest updateRequest, Authentication auth) {
        clientService.throwIfNotPermitted(id, auth);
        return super.update(id, updateRequest, auth);
    }
}
