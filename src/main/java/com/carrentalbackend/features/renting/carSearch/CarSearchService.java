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
                .filter(c -> c.getPriceList() != null)
                .filter(c -> checkIfAvailable(c, dateFrom, dateTo, pickUpOfficeId))
                .map(carMapper::toCarRentResponse)
                .peek(response -> response.setPrice(rentingUtil.calculatePrice(response, rentalLength, sameOffices)))
                .collect(Collectors.toSet());
    }

    private boolean checkIfAvailable(Car car, LocalDate dateFrom, LocalDate dateTo, Long pickUpOfficeId) {
        boolean carReserved = checkInterferingReservations(car, dateTo, dateFrom);
        if(carReserved)
            return false;
        return checkIfCarWillBeInChosenOffice(car, dateFrom, pickUpOfficeId);
    }

    private boolean checkIfCarWillBeInChosenOffice(Car car, LocalDate dateFrom, Long pickUpOfficeId) {

        List<Reservation> reservations = getNotRealizedCarReservations(car, dateFrom);
        Optional<Reservation> lastReservation = reservations.stream()
                .filter(r -> r.getReturnOffice() != null)
                .max(Comparator.comparing(Reservation::getDateTo));
        return lastReservation
                .map(reservation -> compareOffices(reservation.getReturnOffice(), pickUpOfficeId))
                .orElseGet(() -> compareOffices(car.getCurrentOffice(), pickUpOfficeId));
    }

    private List<Reservation> getNotRealizedCarReservations(Car car, LocalDate dateFrom) {
        return reservationRepository.findAllByCar_IdAndDateToBeforeAndStatusNot(car.getId(), dateFrom, ReservationStatus.REALIZED);
    }

    private boolean checkInterferingReservations(Car car, LocalDate dateTo, LocalDate dateFrom) {
        return reservationRepository.existsByDateFromBeforeAndDateToGreaterThanEqualAndCar_IdAndStatusNot(dateTo, dateFrom, car.getId(), ReservationStatus.REALIZED);

    }


    private boolean compareOffices(Office office, Long pickUpOfficeId) {
        if (office != null) {
            return Objects.equals(office.getId(), pickUpOfficeId);
        }
        return false;
    }

}
