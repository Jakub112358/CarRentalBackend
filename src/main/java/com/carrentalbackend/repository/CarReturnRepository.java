package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.CarReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CarReturnRepository extends JpaRepository<CarReturn, Long> {
    Set<CarReturn> findAllByOffice_Id(Long id);

    List<CarReturn> findAllByEmployee_id(Long id);

    List<CarReturn> findAllByCar_id(Long id);
}
