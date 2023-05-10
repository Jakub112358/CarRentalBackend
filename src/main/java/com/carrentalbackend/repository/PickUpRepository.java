package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.CarPickUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickUpRepository extends JpaRepository <CarPickUp, Long> {

    List<CarPickUp> findAllByBranchOffice_Id(Long officeId);
}
