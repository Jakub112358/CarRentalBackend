package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.CarReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CarReturnRepository extends JpaRepository<CarReturn, Long> {
    Set<CarReturn> findAllByOffice_Id(Long id);
}
