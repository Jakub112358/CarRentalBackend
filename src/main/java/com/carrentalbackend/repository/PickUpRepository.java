package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.PickUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickUpRepository extends JpaRepository <PickUp, Long> {

    List<PickUp> findAllByOffice_Id(Long officeId);

    List<PickUp> findAllByEmployee_id(Long id);
}
