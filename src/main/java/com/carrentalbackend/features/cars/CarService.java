package com.carrentalbackend.features.cars;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.cars.rest.CarMapper;
import com.carrentalbackend.features.cars.rest.CarRequest;
import com.carrentalbackend.features.cars.rest.CarUpdateTool;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.features.renting.CarSearchByCriteriaRequest;
import com.carrentalbackend.features.renting.CarRentResponse;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.CompanyRepository;
import com.carrentalbackend.repository.PriceListRepository;
import com.carrentalbackend.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class CarService extends CrudService<Car, CarRequest> {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final PriceListRepository priceListRepository;
    private final CompanyRepository companyRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository,
                      CarMapper carMapper,
                      ReservationRepository reservationRepository,
                      PriceListRepository priceListRepository,
                      CompanyRepository companyRepository,
                      CarUpdateTool carUpdateTool) {
        super(carRepository, carMapper, carUpdateTool);
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.reservationRepository = reservationRepository;
        this.priceListRepository = priceListRepository;
        this.companyRepository = companyRepository;
    }

    public Set<Response> findAllByOfficeId(Long officeId) {
        return carRepository.findAllByCurrentOffice_Id(officeId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    //TODO implement method
    @Override
    public void deleteById(Long id) {
    }

    public Set<CarRentResponse> findByAvailableInDatesAndCriteria(LocalDate dateFrom, LocalDate dateTo, Long pickUpOfficeId, Long returnOfficeId, CarSearchByCriteriaRequest criteria) {
        //TODO: implement criteria
        List<Car> cars = carRepository.findAllByStatusIsNot(CarStatus.UNAVAILABLE);
        int rentalLength = calculateRentalLength(dateFrom, dateTo);
        boolean sameOffices = Objects.equals(returnOfficeId, pickUpOfficeId);
        return cars.stream()
                .filter(c -> checkIfAvailable(c, dateFrom, dateTo, pickUpOfficeId))
                .map(carMapper::toCarRentResponse)
                .peek(response -> calculateAndSetPrice(response, rentalLength, sameOffices))
                .collect(Collectors.toSet());
    }

    private void calculateAndSetPrice(CarRentResponse rentResponse, int rentalLength, boolean sameOffices) {
        PriceList priceList = getPriceList(rentResponse);
        double currentPrice = getCurrentPrice(rentalLength, priceList);
        BigDecimal totalPrice = calculateTotalPrice(rentalLength, currentPrice, sameOffices);
        rentResponse.setPrice(totalPrice);

    }

    private BigDecimal calculateTotalPrice(int rentalLength, double currentPrice, boolean sameOffices) {
        //TODO get this company id from user?
        double extraCharge;
        if (sameOffices) {
            extraCharge = 0.0;
        } else {
            extraCharge = companyRepository
                    .findById(1L)
                    .orElseThrow(() -> new ResourceNotFoundException(1L))
                    .getDifferentOfficesExtraCharge();
        }
        return BigDecimal.valueOf(rentalLength * currentPrice + extraCharge);
    }

    private double getCurrentPrice(int rentalLength, PriceList priceList) {
        if (rentalLength < 7)
            return priceList.getPricePerDay();
        else if (rentalLength < 30)
            return priceList.getPricePerWeek();
        else
            return priceList.getPricePerMonth();
    }

    private PriceList getPriceList(CarRentResponse rentDto) {
        return priceListRepository.findById(rentDto.getPriceListId())
                .orElseThrow(() -> new ResourceNotFoundException(rentDto.getPriceListId()));
    }

    private int calculateRentalLength(LocalDate dateFrom, LocalDate dateTo) {
        return (int) DAYS.between(dateFrom, dateTo) + 1;
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
                .orElseGet(() -> compareOffices(car.getCurrentOffice(), pickUpOfficeId));
    }

    private boolean compareOffices(Office office, Long pickUpOfficeId) {
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