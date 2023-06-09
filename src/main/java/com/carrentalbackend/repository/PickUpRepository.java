package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickUpRepository extends JpaRepository<PickUp, Long> {

    List<PickUp> findAllByOffice_Id(Long officeId);

    List<PickUp> findAllByEmployee_id(Long id);

    List<PickUp> findAllByCar_id(Long id);

    List<PickUp> findAllByReservation_Id(Long id);

    List<PickUp> findAllByOffice_IdAndStatus(Long officeId, RentalActionStatus status);
}
