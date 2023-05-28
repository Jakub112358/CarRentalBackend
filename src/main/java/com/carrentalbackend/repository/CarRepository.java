package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.enumeration.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    List<Car> findAllByCurrentOffice_Id(Long id);

    List<Car> findAllByCurrentOfficeIsNull();

    List<Car> findAllByStatusIsNot(CarStatus carStatus);

    List<Car> findAllByPriceListId(Long id);

}
