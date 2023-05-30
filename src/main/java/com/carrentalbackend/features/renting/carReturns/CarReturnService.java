package com.carrentalbackend.features.renting.carReturns;

import com.carrentalbackend.exception.InvalidReservationDataException;
import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.carReturns.rest.CarReturnMapper;
import com.carrentalbackend.features.renting.carReturns.rest.CarReturnResponse;
import com.carrentalbackend.features.renting.carReturns.rest.CarReturnUpdateRequest;
import com.carrentalbackend.features.renting.carReturns.rest.CarReturnUpdateTool;
import com.carrentalbackend.model.entity.CarReturn;
import com.carrentalbackend.model.entity.Finances;
import com.carrentalbackend.model.entity.Income;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarReturnService {
    private final CarReturnRepository carReturnRepository;
    private final FinancesRepository financesRepository;
    private final ReservationRepository reservationRepository;
    private final IncomeRepository incomeRepository;
    private final CarReturnMapper carReturnMapper;
    private final CarReturnUpdateTool updateTool;

    public Set<Response> findAll() {
        return carReturnRepository
                .findAll()
                .stream()
                .map(carReturnMapper::toResponse)
                .collect(Collectors.toSet());
    }

    public Set<Response> findAllByOfficeId(Long officeId) {
        return carReturnRepository
                .findAllByOffice_Id(officeId)
                .stream()
                .map(carReturnMapper::toResponse)
                .collect(Collectors.toSet());
    }


    public CarReturnResponse update(Long id, CarReturnUpdateRequest request) {

        CarReturn instance = carReturnRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateTool.updateEntity(instance, request);
        CarReturnResponse response = carReturnMapper.toResponse(instance);

        if(request.getExtraCharge() != null)
            saveExtraCharge(response);
        return response;
    }

    public Response findById(Long id) {
        return carReturnMapper.toResponse(carReturnRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    private void saveExtraCharge(CarReturnResponse carReturnResponse) {
        Long reservationId = carReturnResponse.getReservationId();
        if (reservationId == null)
            throw new InvalidReservationDataException("missing reservation id exception");

        Reservation reservation = reservationRepository.getReferenceById(carReturnResponse.getReservationId());
        Finances finances = financesRepository.findFirstByIdIsNotNull().orElseThrow(() -> new ResourceNotFoundException(1L));

        Income income = Income.builder()
                .incomeValue(carReturnResponse.getExtraCharge())
                .reservation(reservation)
                .finances(finances)
                .build();

        incomeRepository.save(income);
    }

}
