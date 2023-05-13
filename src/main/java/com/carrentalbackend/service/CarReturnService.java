package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.crudDto.CarReturnDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.mapper.CarReturnMapper;
import com.carrentalbackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarReturnService extends CrudService<CarReturn, CarReturnDto> {
    private final CarReturnRepository carReturnRepository;
    private final FinancesRepository financesRepository;
    private final ReservationRepository reservationRepository;
    private final IncomeRepository incomeRepository;
    private final CarRepository carRepository;

    public CarReturnService(CarReturnRepository carReturnRepository,
                            CarReturnMapper mapper,
                            FinancesRepository financesRepository,
                            ReservationRepository reservationRepository,
                            IncomeRepository incomeRepository,
                            CarRepository carRepository) {
        super(carReturnRepository, mapper);
        this.carReturnRepository = carReturnRepository;
        this.financesRepository = financesRepository;
        this.reservationRepository = reservationRepository;
        this.incomeRepository = incomeRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void deleteById(Long id) {
        //TODO: implement
    }

    public Set<CarReturnDto> findAllByOfficeId(Long officeId) {
        return carReturnRepository.findAllByOffice_Id(officeId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public CarReturnDto update(Long id, CarReturnDto requestDto) {

        CarReturnDto updatedCarDto = super.update(id, requestDto);
        saveExtraCharge(updatedCarDto);
        updateMileage(requestDto, updatedCarDto.getCarId());
        updatedCarDto.setMileage(requestDto.getMileage());

        return updatedCarDto;
    }

    private void updateMileage(CarReturnDto carReturnDto, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(()->new ResourceNotFoundException(carReturnDto.getCarId()));
        car.setMileage(carReturnDto.getMileage());
    }

    private void saveExtraCharge(CarReturnDto requestDto) {
        Reservation reservation = requestDto.getReservationId() != null ? reservationRepository.getReferenceById(requestDto.getReservationId()) : null;
        //TODO finances id from logged user data?
        Finances finances = financesRepository.getReferenceById(1L);

        Income income = Income.builder()
                .incomeValue(requestDto.getExtraCharge())
                .reservation(reservation)
                .finances(finances)
                .build();

        incomeRepository.save(income);
    }
}
