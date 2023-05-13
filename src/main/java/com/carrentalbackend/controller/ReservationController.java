package com.carrentalbackend.controller;

import com.carrentalbackend.model.rest.request.create.ReservationCreateRequest;
import com.carrentalbackend.model.rest.request.update.ReservationUpdateRequest;
import com.carrentalbackend.model.rest.response.ReservationClientResponse;
import com.carrentalbackend.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.carrentalbackend.controller.ApiConstraints.RESERVATION;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(RESERVATION)
public class ReservationController extends CrudController<ReservationCreateRequest, ReservationUpdateRequest> {
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
