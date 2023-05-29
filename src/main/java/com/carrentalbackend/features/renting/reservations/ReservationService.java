package com.carrentalbackend.features.renting.reservations;

import com.carrentalbackend.exception.ForbiddenResourceException;
import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.RentingUtil;
import com.carrentalbackend.features.renting.RentingValidation;
import com.carrentalbackend.features.renting.reservations.rest.ReservationMapper;
import com.carrentalbackend.features.renting.reservations.rest.ReservationCreateRequest;
import com.carrentalbackend.features.renting.reservations.rest.ReservationResponse;
import com.carrentalbackend.features.renting.reservations.rest.ReservationUpdateTool;
import com.carrentalbackend.model.entity.Client;
import com.carrentalbackend.model.entity.Finances;
import com.carrentalbackend.model.entity.Income;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService extends CrudService<Reservation, ReservationCreateRequest, ReservationCreateRequest> {
    private final IncomeRepository incomeRepository;
    private final ReservationRepository reservationRepository;
    private final FinancesRepository financesRepository;
    private final ReservationMapper reservationMapper;
    private final RentingValidation rentingValidation;
    private final RentingUtil rentingUtil;
    private final ClientRepository clientRepository;

    public ReservationService(ReservationRepository repository,
                              ReservationMapper mapper,
                              IncomeRepository incomeRepository,
                              ReservationRepository reservationRepository,
                              FinancesRepository financesRepository,
                              ReservationUpdateTool updateTool,
                              RentingValidation rentingValidation,
                              RentingUtil rentingUtil,
                              ClientRepository clientRepository) {
        super(repository, mapper, updateTool);
        this.incomeRepository = incomeRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = mapper;
        this.financesRepository = financesRepository;
        this.rentingValidation = rentingValidation;
        this.rentingUtil = rentingUtil;
        this.clientRepository = clientRepository;
    }


    @Override
    public Response save(ReservationCreateRequest request) {
        validateRequest(request);
        Response response = super.save(request);
        addIncome(response);
        return response;
    }

    private void validateRequest(ReservationCreateRequest request) {
        rentingValidation.throwIfInvalidRentingDatesOrder(request.getDateFrom(), request.getDateTo());

        var rentalLength = rentingUtil.calculateRentalLength(request.getDateFrom(), request.getDateTo());
        var sameOffices = request.getPickUpOfficeId().equals(request.getReturnOfficeId());
        var expectedPrice = rentingUtil.calculatePrice(request.getCarId(), rentalLength, sameOffices);
        rentingValidation.throwIfInvalidPrice(expectedPrice, request);
    }

    @Override
    public Response update(Long id, ReservationCreateRequest request) {
        //TODO: should check for request sender and add extra charge only if user cancel his reservation
        performFinancialOperations(id, request);
        return super.update(id, request);
    }

    public Set<ReservationResponse> findByClientId(Long clientId) {
        return reservationRepository
                .findAllByClient_Id(clientId)
                .stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteById(Long id) {
    }


    private void addIncome(Response response) {
        Income income = reservationMapper.reservationResponseToIncome(response);
        incomeRepository.save(income);
    }




    private void performFinancialOperations(Long id, ReservationCreateRequest request) {

        Reservation reservationBefore = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        ReservationStatus statusBefore = reservationBefore.getStatus();
        //ReservationStatus statusAfter = request.getStatus();
        //TODO: extract update request with status!!!!
        ReservationStatus statusAfter = ReservationStatus.REALIZED;

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

    public void throwIfNotPermittedWithReservationId(Long reservationId, Authentication auth) {
        //user with role CLIENT is restricted only to read/update his own data
        Long clientId = extractClientId(reservationId);
        throwIfNotPermittedWithClientId(clientId, auth);
    }

    private Long extractClientId(Long reservationId) {
        var reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException(reservationId));
        System.out.println(reservation);
        System.out.println(reservation.getClient());
        return reservation.getClient().getId();
    }

    private void throwIfIdDoesNotMatchUser(Long id, String name) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if (!client.getEmail().equals(name))
            throw new ForbiddenResourceException("client", id);
    }

    private boolean authHasRole(Authentication auth, String role) {
        return auth.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

    public void throwIfNotPermittedWithClientId(Long clientId, Authentication auth) {
        //user with role CLIENT is restricted only to read/update his own data
        if (authHasRole(auth, "ROLE_CLIENT"))
            throwIfIdDoesNotMatchUser(clientId, auth.getName());
    }

    public void throwIfNotAdminOrEmployee(Authentication auth) {
        if(!(authHasRole(auth, "ROLE_ADMIN") || authHasRole(auth, "ROLE_EMPLOYEE")))
            throw new ForbiddenResourceException("client", 0L);
    }
}
