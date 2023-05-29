package com.carrentalbackend.features.cars;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.cars.rest.CarMapper;
import com.carrentalbackend.features.cars.rest.CarRequest;
import com.carrentalbackend.features.cars.rest.CarUpdateTool;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.CarReturn;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarService extends CrudService<Car, CarRequest, CarRequest> {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final PickUpRepository pickUpRepository;
    private final CarReturnRepository carReturnRepository;


    public CarService(CarRepository carRepository,
                      CarMapper carMapper,
                      ReservationRepository reservationRepository,
                      CarUpdateTool carUpdateTool,
                      PickUpRepository pickUpRepository,
                      CarReturnRepository carReturnRepository) {
        super(carRepository, carMapper, carUpdateTool);
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
        this.pickUpRepository = pickUpRepository;
        this.carReturnRepository = carReturnRepository;
    }

    public Set<Response> findAllByOfficeId(Long officeId) {
        return carRepository.findAllByCurrentOffice_Id(officeId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteById(Long id) {
        throwIfCarDoesNotExist(id);

        nullCarInPickUps(id);
        nullCarInCarReturns(id);
        nullCarInReservations(id);

        carRepository.deleteById(id);
    }

    private void nullCarInReservations(Long id) {
        List<Reservation> reservations = reservationRepository.findAllByCar_id(id);
        reservations.forEach(r -> r.setCar(null));
    }

    private void nullCarInCarReturns(Long id) {
        List<CarReturn> carReturns = carReturnRepository.findAllByCar_id(id);
        carReturns.forEach(cr -> cr.setCar(null));
    }

    private void nullCarInPickUps(Long id) {
        List<PickUp> pickups = pickUpRepository.findAllByCar_id(id);
        pickups.forEach(pu -> pu.setCar(null));
    }

    private void throwIfCarDoesNotExist(Long id) {
        if (!carRepository.existsById(id))
            throw new ResourceNotFoundException(id);
    }
}
