package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.CarDto;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.mapper.CarMapper;
import com.carrentalbackend.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService extends CrudService<Car, CarDto> {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository, CarMapper carMapper) {
        super(carRepository, carMapper);
        this.carRepository = carRepository;
    }

    public List<CarDto> findAllByBranchOfficeId (Long branchOfficeId){
        return carRepository.findAllByCurrentBranchOffice_Id(branchOfficeId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }


    //TODO implement method
    @Override
    public void deleteById(Long id) {
    }
}
