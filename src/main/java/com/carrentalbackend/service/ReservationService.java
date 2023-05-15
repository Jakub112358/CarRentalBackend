package com.carrentalbackend.service;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.entity.Finances;
import com.carrentalbackend.model.entity.Income;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.model.rest.request.create.ReservationCreateRequest;
import com.carrentalbackend.model.rest.request.update.ReservationUpdateRequest;
import com.carrentalbackend.model.rest.response.ReservationClientResponse;
import com.carrentalbackend.model.rest.response.ReservationResponse;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.repository.CompanyRepository;
import com.carrentalbackend.repository.FinancesRepository;
import com.carrentalbackend.repository.IncomeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import com.carrentalbackend.service.mapper.ReservationMapper;
import com.carrentalbackend.service.validator.ReservationValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService extends CrudService<Reservation, ReservationUpdateRequest, ReservationCreateRequest> {
    private final ReservationValidator reservationValidator;
    private final IncomeRepository incomeRepository;
    private final ReservationRepository reservationRepository;
    private final CompanyRepository companyRepository;
    private final FinancesRepository financesRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository repository,
                              ReservationMapper mapper,
                              ReservationValidator reservationValidator,
                              IncomeRepository incomeRepository,
                              ReservationRepository reservationRepository,
                              CompanyRepository companyRepository,
                              FinancesRepository financesRepository) {
        super(repository, mapper);
        this.reservationValidator = reservationValidator;
        this.incomeRepository = incomeRepository;
        this.reservationRepository = reservationRepository;
        this.companyRepository = companyRepository;
        this.reservationMapper = mapper;
        this.financesRepository = financesRepository;
    }


    @Override
    public Response save(ReservationCreateRequest request) {
        reservationValidator.validate(request);
        Response response = super.save(request);
        addIncome(response);
        return response;
    }

    private void addIncome(Response response) {
        Income income = reservationToIncome(response);
        incomeRepository.save(income);
    }

    private Income reservationToIncome(Response response) {
        //TODO: check this class before
        ReservationResponse reservationResponse = (ReservationResponse) response;
        //TODO company id hardcoded?
        Reservation reservation = reservationRepository.getReferenceById(response.getId());
        Finances finances = companyRepository.findById(1L).orElseThrow().getFinances();
        return Income.builder()
                .incomeValue(reservationResponse.getPrice())
                .reservation(reservation)
                .finances(finances)
                .build();
    }

    @Override
    public void deleteById(Long id) {
    }

    public Set<ReservationClientResponse> findByClientId(Long clientId) {
        return reservationRepository
                .findAllByClient_Id(clientId)
                .stream()
                .map(reservationMapper::toReservationClientResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Response update(Long id, ReservationUpdateRequest request) {
        //TODO: should check for request sender and add extra charge only if user cancel his reservation
        performFinancialOperations(id, request);
        return super.update(id, request);
    }


    private void performFinancialOperations(Long id, ReservationUpdateRequest request) {

        Reservation reservationBefore = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        ReservationStatus statusBefore = reservationBefore.getStatus();
        ReservationStatus statusAfter = request.getStatus();

        if (ReservationStatus.PLANNED.equals(statusBefore) && ReservationStatus.CANCELLED.equals(statusAfter)) {
            performPayback(reservationBefore);
        }
    }

    //TODO consider finances id, split to more methods
    private void performPayback(Reservation reservationBefore) {
        BigDecimal price = reservationBefore.getPrice();
        double extraChargeRatio = calculateExtraChargeRatio(reservationBefore);
        BigDecimal refundValue = BigDecimal.valueOf(price.doubleValue() * (1.0 - extraChargeRatio) * (-1)).setScale(2, RoundingMode.CEILING);
        Finances finances = financesRepository.getReferenceById(1L);
        Income refund = new Income(0L, refundValue, reservationBefore, finances);
        incomeRepository.save(refund);
    }

    private double calculateExtraChargeRatio(Reservation reservationBefore) {
        LocalDate dateNow = LocalDate.now();
        LocalDate reservationStart = reservationBefore.getDateFrom();
        long daysDifference = DAYS.between(dateNow, reservationStart);
        if (daysDifference > 10) {
            return 0;
        } else if (daysDifference > 2) {
            return 0.1;
        } else {
            return 0.5;
        }
    }
}
