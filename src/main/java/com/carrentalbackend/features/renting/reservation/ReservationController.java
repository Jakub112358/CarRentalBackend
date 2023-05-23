package com.carrentalbackend.features.renting.reservation;

import com.carrentalbackend.features.generics.CrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.config.ApiConstraints.ORIGIN;
import static com.carrentalbackend.config.ApiConstraints.RESERVATION;

@RestController
@CrossOrigin(origins = ORIGIN)
@RequestMapping(RESERVATION)
public class ReservationController extends CrudController<ReservationRequest> {
    private final ReservationService reservationService;

    public ReservationController(ReservationService service) {
        super(service);
        this.reservationService = service;
    }

    @GetMapping(params = "clientId")
    public ResponseEntity<Set<ReservationClientResponse>> findByClientId(@RequestParam Long clientId) {
        return ResponseEntity.ok(reservationService.findByClientId(clientId));
    }
}
