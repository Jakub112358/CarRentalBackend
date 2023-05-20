package com.carrentalbackend.features.renting.carReturns;

import com.carrentalbackend.exception.InvalidCastException;
import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarReturnService extends CrudService<CarReturn, CarReturnUpdateRequest, CarReturnCreateRequest> {
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

    public Set<Response> findAllByOfficeId(Long officeId) {
        return carReturnRepository.findAllByOffice_Id(officeId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public CarReturnResponse update(Long id, CarReturnUpdateRequest request) {

        Response response = super.update(id, request);

        checkIfInstance(response, CarReturnResponse.class);
        CarReturnResponse carReturnResponse = (CarReturnResponse) response;

        saveExtraChargeAndMileage(carReturnResponse, request);

        carReturnResponse.setMileage(request.getMileage());

        return carReturnResponse;
    }


    private void saveExtraChargeAndMileage(CarReturnResponse carReturnResponse, CarReturnUpdateRequest carReturnUpdateRequest) {
        saveExtraCharge(carReturnResponse);
        updateMileage(carReturnUpdateRequest, carReturnResponse.getCarId());
    }


    private void updateMileage(CarReturnUpdateRequest request, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException(carId));
        car.setMileage(request.getMileage());
    }

    private void saveExtraCharge(CarReturnResponse carReturnResponse) {

        Reservation reservation = carReturnResponse.getReservationId() != null ? reservationRepository.getReferenceById(carReturnResponse.getReservationId()) : null;
        //TODO finances id from logged user data?
        Finances finances = financesRepository.getReferenceById(1L);

        Income income = Income.builder()
                .incomeValue(carReturnResponse.getExtraCharge())
                .reservation(reservation)
                .finances(finances)
                .build();

        incomeRepository.save(income);
    }

    private void checkIfInstance(Object object, Class<?> castClass) {
        if (!castClass.isInstance(object))
            throw new InvalidCastException(castClass.getSimpleName());
    }
}