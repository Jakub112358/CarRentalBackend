package com.carrentalbackend.features.clients;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.generics.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENT")))
            clientService.checkIfIdMatchesUser(id, auth.getName());

        return super.findById(id, auth);
    }
}
