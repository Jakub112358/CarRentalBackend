package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.crudDto.CarDto;
import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.model.mapper.CarMapper;
import com.carrentalbackend.model.request.CarSearchRequest;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CarService extends CrudService<Car, CarDto> {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, CarMapper carMapper, ReservationRepository reservationRepository) {
        super(carRepository, carMapper);
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.reservationRepository = reservationRepository;
    }

    public List<CarDto> findAllByBranchOfficeId(Long branchOfficeId) {
        return carRepository.findAllByCurrentBranchOffice_Id(branchOfficeId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    //TODO implement method
    @Override
    public void deleteById(Long id) {
    }

    public List<CarDto> findByAvailableInDatesAndCriteria(LocalDate dateFrom, LocalDate dateTo, Long pickUpOfficeId, CarSearchRequest criteria) {
        //TODO: implement criteria
        List<Car> cars = carRepository.findAllByStatusIsNot(CarStatus.UNAVAILABLE);
        return cars.stream()
                .filter(c -> checkIfAvailable(c, dateFrom, dateTo, pickUpOfficeId))
                .map(carMapper::toDto)
                .toList();
    }

    private boolean checkIfAvailable(Car car, LocalDate dateFrom, LocalDate dateTo, Long pickUpOfficeId) {
        List<Reservation> reservations = getNotRealizedReservations(car, dateTo);
        if (!checkIfReservationsDontInterfere(dateFrom, reservations))
            return false;
        return checkIfCarWillBeInChosenOffice(pickUpOfficeId, reservations, car);
    }

    private boolean checkIfCarWillBeInChosenOffice(Long pickUpOfficeId, List<Reservation> reservations, Car car) {
        Optional<Reservation> lastReservation = reservations.stream().max(Comparator.comparing(Reservation::getDateTo));
        return lastReservation
                .map(reservation -> compareOffices(reservation.getReturnOffice(), pickUpOfficeId))
                .orElseGet(() -> compareOffices(car.getCurrentBranchOffice(), pickUpOfficeId));
    }

    private boolean compareOffices(BranchOffice office, Long pickUpOfficeId) {
        if (office != null) {
            return office.getId() == pickUpOfficeId;
        }
        return false;
    }

    private boolean checkIfReservationsDontInterfere(LocalDate newDateFrom, List<Reservation> reservations) {
        return reservations.stream().noneMatch(r -> newDateFrom.isAfter(r.getDateTo()));
    }

    private List<Reservation> getNotRealizedReservations(Car car, LocalDate dateTo) {
        return reservationRepository.findAllByDateFromBeforeAndCar_IdAndStatusNot(dateTo, car.getId(), ReservationStatus.REALIZED);
    }
}
