package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByDateFromBeforeAndDateToGreaterThanEqualAndCar_IdAndStatusNot(LocalDate newDateTo, LocalDate newDateFrom, Long carId, ReservationStatus status);
    List<Reservation> findAllByCar_IdAndDateToBeforeAndStatusNot(Long carId, LocalDate newDateFrom, ReservationStatus status);

    List<Reservation> findAllByClient_Id(Long clientId);

    List<Reservation> findAllByReturnOffice_id(Long id);

    List<Reservation> findAllByPickUpOffice_id(Long id);

    List<Reservation> findAllByCar_id(Long id);

    List<Reservation> findAllByClient_IdAndStatusNotAndStatusNot(Long clientId, ReservationStatus status1, ReservationStatus status2);
}
