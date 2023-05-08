package com.carrentalbackend.controller;

import com.carrentalbackend.model.dto.crudDto.ReservationDto;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.service.ReservationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrentalbackend.controller.ApiConstraints.RESERVATION;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(RESERVATION)
public class ReservationController extends CrudController<Reservation, ReservationDto> {

    public ReservationController(ReservationService service) {
        super(service);
    }
}
