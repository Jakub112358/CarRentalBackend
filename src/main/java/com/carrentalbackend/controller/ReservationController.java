package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.ReservationDto;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.rest.ReservationClientResponse;
import com.carrentalbackend.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.carrentalbackend.controller.ApiConstraints.RESERVATION;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(RESERVATION)
public class ReservationController extends CrudController<Reservation, ReservationDto> {
    private final ReservationService reservationService;

    public ReservationController(ReservationService service) {
        super(service);
        this.reservationService = service;
    }

    @GetMapping(params = "clientId")
    public ResponseEntity<List<ReservationClientResponse>> findByClientId(@RequestParam Long clientId) {
        return ResponseEntity.ok(reservationService.findByClientId(clientId));
    }
}
