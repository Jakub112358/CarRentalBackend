package com.carrentalbackend.features.clients;

import com.carrentalbackend.exception.ForbiddenResourceException;
import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.clients.rest.ClientCreateRequest;
import com.carrentalbackend.features.clients.rest.ClientMapper;
import com.carrentalbackend.features.clients.rest.ClientUpdateRequest;
import com.carrentalbackend.features.clients.rest.ClientUpdateTool;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.repository.ClientRepository;
import com.carrentalbackend.repository.ReservationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService extends CrudService<Client, ClientCreateRequest, ClientUpdateRequest> {
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;

    public ClientService(ClientRepository repository,
                         ClientMapper mapper,
                         ClientUpdateTool updateTool,
                         ReservationRepository reservationRepository) {
        super(repository, mapper, updateTool);
        this.clientRepository = repository;
        this.reservationRepository = reservationRepository;

    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id))
            throw new ResourceNotFoundException(id);
        nullClientInReservations(id);
        clientRepository.deleteById(id);
    }

    public void throwIfNotPermitted(Long id, Authentication auth) {
        //user with role CLIENT is restricted only to read/update his own data, users with other roles can perform all FindById requests
        if (authHasRole(auth, "ROLE_CLIENT"))
            throwIfIdDoesntMatchUser(id, auth.getName());
    }

    private void throwIfIdDoesntMatchUser(Long id, String name) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if (!client.getEmail().equals(name))
            throw new ForbiddenResourceException("client", id);
    }

    private void nullClientInReservations(Long id) {
        List<Reservation> reservations = this.reservationRepository.findAllByClient_Id(id);
        reservations.forEach(r -> r.setClient(null));
    }

    private boolean authHasRole(Authentication auth, String role) {
        return auth.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

}
