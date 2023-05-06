package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByDateFromBeforeAndCar_IdAndStatusNot(LocalDate date, Long carId, ReservationStatus status);
}
