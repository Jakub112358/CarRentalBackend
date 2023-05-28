package com.carrentalbackend.features.renting.carSearch;

import com.carrentalbackend.features.cars.rest.CarMapper;
import com.carrentalbackend.features.renting.RentingUtil;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarSearchService {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final CarMapper carMapper;
    private final RentingUtil rentingUtil;

    public List<Car> findNotUnavailable() {
        return carRepository.findAllByStatusIsNot(CarStatus.UNAVAILABLE);
    }

    public List<Car> findByCriteria(CarSearchByCriteriaRequest criteria) {
        Specification<Car> specification = new CarSpecification(criteria);
        return carRepository.findAll(specification);
    }

    public Set<CarSearchResponse> findByAvailableInTerm(LocalDate dateFrom, LocalDate dateTo, Long pickUpOfficeId, Long returnOfficeId, List<Car> cars) {

        int rentalLength = rentingUtil.calculateRentalLength(dateFrom, dateTo);
        boolean sameOffices = Objects.equals(returnOfficeId, pickUpOfficeId);

        return cars.stream()
                .filter(c -> checkIfAvailable(c, dateFrom, dateTo, pickUpOfficeId))
                .map(carMapper::toCarRentResponse)
                .peek(response -> response.setPrice(rentingUtil.calculatePrice(response, rentalLength, sameOffices)))
                .collect(Collectors.toSet());
    }

    private boolean checkIfAvailable(Car car, LocalDate dateFrom, LocalDate dateTo, Long pickUpOfficeId) {
        List<Reservation> reservations = getNotRealizedReservations(car, dateTo);
        if (!checkIfReservationsDoNotInterfere(dateFrom, reservations))
            return false;
        return checkIfCarWillBeInChosenOffice(pickUpOfficeId, reservations, car);
    }

    private boolean checkIfCarWillBeInChosenOffice(Long pickUpOfficeId, List<Reservation> reservations, Car car) {
        Optional<Reservation> lastReservation = reservations.stream().max(Comparator.comparing(Reservation::getDateTo));
        return lastReservation
                .map(reservation -> compareOffices(reservation.getReturnOffice(), pickUpOfficeId))
                .orElseGet(() -> compareOffices(car.getCurrentOffice(), pickUpOfficeId));
    }

    private boolean compareOffices(Office office, Long pickUpOfficeId) {
        if (office != null) {
            return office.getId() == pickUpOfficeId;
        }
        return false;
    }

    private boolean checkIfReservationsDoNotInterfere(LocalDate newDateFrom, List<Reservation> reservations) {
        return reservations.stream().noneMatch(r -> newDateFrom.isAfter(r.getDateTo()));
    }

    private List<Reservation> getNotRealizedReservations(Car car, LocalDate dateTo) {
        return reservationRepository.findAllByDateFromBeforeAndCar_IdAndStatusNot(dateTo, car.getId(), ReservationStatus.REALIZED);
    }

}
