package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.ReservationDto;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.mapper.ReservationMapper;
import com.carrentalbackend.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends CrudService<Reservation, ReservationDto> {

    public ReservationService(ReservationRepository repository, ReservationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void deleteById(Long id) {

    }
}
