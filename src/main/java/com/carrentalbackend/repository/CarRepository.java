package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.enumeration.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByCurrentBranchOffice_Id(Long id);

    List<Car> findAllByCurrentBranchOfficeIsNull();
    List<Car> findAllByStatusIsNot(CarStatus carStatus);

}
