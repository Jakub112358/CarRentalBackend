package com.carrentalbackend.features.renting.reservations;

import com.carrentalbackend.features.generics.CrudController;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.reservations.rest.ReservationCreateRequest;
import com.carrentalbackend.features.renting.reservations.rest.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.ORIGIN;
import static com.carrentalbackend.config.ApiConstraints.RESERVATION;

@RestController
@CrossOrigin(origins = ORIGIN)
@RequestMapping(RESERVATION)
public class ReservationController extends CrudController<ReservationCreateRequest, ReservationCreateRequest> {
    private final ReservationService reservationService;

    public ReservationController(ReservationService service) {
        super(service);
        this.reservationService = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<Set<Response>> findAll(Authentication auth) {
        reservationService.throwIfNotAdminOrEmployee(auth);
        return super.findAll(auth);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id, Authentication auth) {
        reservationService.throwIfNotPermittedWithReservationId(id, auth);
        return super.findById(id, auth);
    }

    @GetMapping(params = "clientId")
    public ResponseEntity<Set<ReservationResponse>> findByClientId(@RequestParam Long clientId, Authentication auth) {
        reservationService.throwIfNotPermittedWithClientId(clientId, auth);
        return ResponseEntity.ok(reservationService.findByClientId(clientId));
    }
}
