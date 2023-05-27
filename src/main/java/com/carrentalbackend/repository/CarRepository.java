package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.enumeration.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByCurrentOffice_Id(Long id);

    List<Car> findAllByCurrentOfficeIsNull();

    Set<Car> findAllByStatusIsNot(CarStatus carStatus);

    List<Car> findAllByPriceListId(Long id);

}
